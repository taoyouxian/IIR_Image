#coding=utf-8
from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import tensorflow as tf
import numpy as np
import argparse
import math
from scipy import misc
import time

import os
import sys
import facenet

def prewhiten(x):
    mean = np.mean(x)
    std = np.std(x)
    std_adj = np.maximum(std, 1.0/np.sqrt(x.size))
    y = np.multiply(np.subtract(x, mean), 1/std_adj)
    return y  

def crop(image, random_crop, image_size):
    if image.shape[1]>image_size:
        sz1 = int(image.shape[1]//2)
        sz2 = int(image_size//2)
        if random_crop:
            diff = sz1-sz2
            (h, v) = (np.random.randint(-diff, diff+1), np.random.randint(-diff, diff+1))
        else:
            (h, v) = (0,0)
        image = image[(sz1-sz2+v):(sz1+sz2+v),(sz1-sz2+h):(sz1+sz2+h),:]
    return image
  
def flip(image, random_flip):
    if random_flip and np.random.choice([True, False]):
        image = np.fliplr(image)
    return image

def to_rgb(img):
    w, h = img.shape
    ret = np.empty((w, h, 3), dtype=np.uint8)
    ret[:, :, 0] = ret[:, :, 1] = ret[:, :, 2] = img
    return ret

def load_data(filename, start_index, end_index, do_random_crop, do_random_flip, image_size, do_prewhiten=True):
    fw = open(filename, 'r')
    lines = fw.readlines()
    num = end_index - start_index
    images = np.zeros((num, image_size, image_size, 3))
    paths = []
    for i in range(start_index, end_index):
        image_path = '../data/' + lines[i].split()[0]
        img = misc.imread(image_path, mode='RGB')
        if img.ndim == 2:
            img = to_rgb(img)
        if do_prewhiten:
            img = prewhiten(img)
        if do_random_crop:
            img = crop(img, do_random_crop, image_size)
        else:
            img = misc.imresize(img, (image_size, image_size))
        if do_random_flip:
            img = flip(img, do_random_flip)
        images[i - start_index,:,:,:] = img
        if i % 1000 == 0:
            print ('Have loaded %d'%i)
    fw.close()
    return images

def main(args): 
    start_time = time.time()
    sw = open(args.feature_path, 'w+')
    
    image_size = args.image_size

    with tf.Graph().as_default():
        config = tf.ConfigProto(allow_soft_placement=True)
        config.gpu_options.allow_growth = True
        with tf.Session(config=config) as sess:

        # Load the model
            facenet.load_model(args.model)
            images_placeholder = tf.get_default_graph().get_tensor_by_name("input:0")
            features = tf.get_default_graph().get_tensor_by_name("InceptionResnetV1/Logits/AvgPool_1a_8x8/AvgPool:0")
#            embeddings  = tf.get_default_graph().get_tensor_by_name("embeddings:0")
            phase_train_placeholder = tf.get_default_graph().get_tensor_by_name("phase_train:0")
        
            feature_size = features.get_shape()[3]

        # Run forward pass to extract features
            print('Runnning forward pass on images')
            batch_size = args.batch_size
            fw = open(args.filename, 'r')
            lines = fw.readlines()
            image_num = len(lines)
            fw.close()
            batch_num = int(math.ceil(1.0*image_num / batch_size))
        # features_arr = np.zeros((image_num, feature_size))
            print ('batch num %d'%batch_num)
            for i in range(batch_num):
                fetures_arr = np.zeros((batch_size, feature_size))
                start_index = i * batch_size
                end_index = min((i+1)*batch_size, image_num)
                images = load_data(args.filename, start_index, end_index, False, False, image_size)
                # inputs = images[start_index:end_index]
                feed_dict = { images_placeholder:images, phase_train_placeholder:False }
                features_arr = sess.run(features[0:end_index-start_index,0,0,:], feed_dict=feed_dict)
#                features_arr = sess.run(embeddings , feed_dict=feed_dict)
                for j in range(end_index-start_index):
                    for k in range(feature_size):
                        sw.writelines(str(features_arr[j][k])+" ")
                    sw.writelines("\n")
                if i % 1 == 0:
                    print ('Have loaded %d batches'%i) 

    sw.close()
    duration = time.time() - start_time
    print ('Total time is %.3f seconds'%duration)

def parse_arguments(argv):
    parser = argparse.ArgumentParser()
    parser.add_argument('--image_size', type=int,
        help='Image size (height, width) in pixels.', default=160)
    parser.add_argument('--batch_size', type=int,
        default=1)
    parser.add_argument('--filename', type=str,
        help='The file containing the image path')
    parser.add_argument('--model', type=str,
        help='Could be either a directory containing the meta_file and ckpt_file or a model protobuf (.pb) file')
    parser.add_argument('--feature_path', type=str,
        help='The file to save features')
    return parser.parse_args(argv)

if __name__ == '__main__':
    main(parse_arguments(sys.argv[1:]))
