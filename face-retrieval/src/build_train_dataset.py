# -*- coding: UTF-8 -*-
import os, sys
import cv2
from PIL import Image, ImageFont, ImageDraw
import numpy as np
import tensorflow as tf
import random
import detect_face
import facenet
import io
import scipy
reload(sys)
sys.setdefaultencoding('utf-8')

minsize = 20
threshold = [0.6, 0.7, 0.7]
factor = 0.709
frame_interval = 1
image_size = 160
model_path = '../model/'
detect_model = os.path.join(model_path, 'detect/')
picture_path = '../data/star_pictures/'
save_name = '../data/project.txt'
save_path = '../data/star_face/'

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

def main():
    sw = io.open(save_name, 'w+', encoding='utf-8')
    with tf.Graph().as_default():
        config = tf.ConfigProto(allow_soft_placement=True, log_device_placement=False)
        config.gpu_options.allow_growth = True
        sess = tf.Session(config=config)
        with sess.as_default():
            # create detect network
            pnet, rnet, onet = detect_face.create_mtcnn(sess, detect_model)
        
        name_dirs = os.listdir(picture_path)
        for name_dir in name_dirs:
            name_path = os.path.join(picture_path, name_dir)
            image_paths = os.listdir(name_path)
            idx = 0
            for image_path in image_paths:
                if image_path.endswith(".txt"):
                    continue
                new_name = 'star_face/' + name_dir + "/img_{:03d}.png".format(idx)
                img = cv2.imread(os.path.join(name_path, image_path))
                if img is None:
                    print os.path.join(name_path, image_path)
                    continue
                # detect
                height, width, shape = img.shape
                bounding_boxes, _ = detect_face.detect_face(img, minsize, pnet, rnet, onet, threshold, factor)
                if len(bounding_boxes) != 1:
                    continue
                for bounding_box in bounding_boxes:
                    bounding_box = bounding_box.astype(int)
                    # crop and align
                    if bounding_box[0] < 0 or bounding_box[1] < 0 or bounding_box[2] >= width or bounding_box[3] >= height:
                        continue
                    croped = img[bounding_box[1]:bounding_box[3], bounding_box[0]:bounding_box[2],]
                    aligned = scipy.misc.imresize(croped, (image_size, image_size), interp='bilinear')
                    # aligned = croped.resize((image_size, image_size))
                    if not os.path.exists(save_path + name_dir):
                        os.makedirs(save_path + name_dir)
                    cv2.imwrite(save_path + name_dir + "/img_{:03d}.png".format(idx), aligned)
                    write_str = (new_name + " star_pictures/" + name_dir + "/" + image_path).encode('utf-8').decode('utf-8')
                    sw.writelines(write_str + "\n")
                idx += 1
    sw.close()

if __name__ == "__main__":
    main()