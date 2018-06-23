# -*- coding: UTF-8 -*-
import os, sys
import cv2
from PIL import Image, ImageFont, ImageDraw
import numpy as np
import pyflann
import scipy
import tensorflow as tf
import random
import detect_face
import facenet

minsize = 20
threshold = [0.6, 0.7, 0.7]
factor = 0.709
image_size = 160

def prewhiten(x):
    mean = np.mean(x)
    std = np.std(x)
    std_adj = np.maximum(std, 1.0/np.sqrt(x.size))
    y = np.multiply(np.subtract(x, mean), 1/std_adj)
    return y

def to_rgb(img):
    w, h = img.shape
    ret = np.empty((w, h, 3), dtype=np.uint8)
    ret[:, :, 0] = ret[:, :, 1] = ret[:, :, 2] = img
    return ret

def mainstream(sess, img_path, idx, store_path, IMAGE_DIR, SAVE_DIR, pnet, rnet, onet, flann, topk=5):
    '''
    params sess: tensorflow session that have been loaded models in
    params img_path: path of image to process
    params idx: index of sarch images
    params pnet, rnet, onet: detect model params
    params flann: index
    params topk: num of nearest result to return
    
    return
    if no valid face: return False
    else: a list of possible idxes [[idxes]]
    '''
    store_path = os.path.join(SAVE_DIR, store_path)
    if not os.path.exists(store_path):
        os.makedirs(store_path)
    final_result = []
    images_placeholder = tf.get_default_graph().get_tensor_by_name("input:0")
    features = tf.get_default_graph().get_tensor_by_name("InceptionResnetV1/Logits/AvgPool_1a_8x8/AvgPool:0")
    phase_train_placeholder = tf.get_default_graph().get_tensor_by_name("phase_train:0")      
    feature_size = features.get_shape()[3]

    img = scipy.misc.imread(os.path.join(IMAGE_DIR, img_path), mode='RGB')
    if img.ndim == 2:
        img = to_rgb(img)
    img = prewhiten(img)
    # detect
    height, width, shape = img.shape
    bounding_boxes, _ = detect_face.detect_face(img, minsize, pnet, rnet, onet, threshold, factor)
    if len(bounding_boxes) < 1:
        return False
    # it is possible to have multiple faces
    valid_num = 0
    for bounding_box in bounding_boxes:
        bounding_box = bounding_box.astype(int)
        # crop and align
        if bounding_box[0] < 0 or bounding_box[1] < 0 or bounding_box[2] >= width or bounding_box[3] >= height:
            continue
        else:
            valid_num += 1
    # no valid faces
    if valid_num == 0:
        return False

    copy = img.copy()
    for bounding_box in bounding_boxes:
        bounding_box = bounding_box.astype(int)
        # crop and align
        if bounding_box[0] < 0 or bounding_box[1] < 0 or bounding_box[2] >= width or bounding_box[3] >= height:
            continue
        cv2.rectangle(copy, (bounding_box[0], bounding_box[1]), (bounding_box[2], bounding_box[3]), (0, 255, 0), 2)
        break
    save_name_1 = store_path + "/" + str(idx) + '_0.jpg'
    print save_name_1
    cv2.imwrite(save_name_1, copy)

    for bounding_box in bounding_boxes:
        bounding_box = bounding_box.astype(int)
        # crop and align
        if bounding_box[0] < 0 or bounding_box[1] < 0 or bounding_box[2] >= width or bounding_box[3] >= height:
            continue
        croped = img[bounding_box[1]:bounding_box[3], bounding_box[0]:bounding_box[2],]
        aligned = scipy.misc.imresize(croped, (image_size, image_size))
        # save process
        save_name_2 = store_path + "/" + str(idx) + '_1.jpg'
        cv2.imwrite(save_name_2, croped)
        save_name_3 = store_path + "/" + str(idx) + '_2.jpg'
        cv2.imwrite(save_name_3, aligned)

        # extract feature
        fetures_arr = np.zeros((1, feature_size))
        images = np.zeros((1, image_size, image_size, 3))
        images[0,:,:,:] = aligned
        feed_dict = { images_placeholder:images, phase_train_placeholder:False }
        features_arr = sess.run(features[0,0,0,:], feed_dict=feed_dict)
        features_arr = np.array(features_arr.reshape(1, -1), dtype=np.float32)
        results, _ = flann.nn_index(features_arr, topk)
        final_result.append(results.tolist())
        break
        
    return final_result

