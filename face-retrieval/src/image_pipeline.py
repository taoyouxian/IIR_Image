#coding=utf-8
import os
from flask import Flask,request
import numpy as np
import pyflann
import tensorflow as tf
import detect_face
import facenet
import image_retrival
import io
import sys
reload(sys)
sys.setdefaultencoding('utf-8')
import time

sess = 0
pnet = 0
rnet = 0
onet = 0
idx_path_dict = {}
new_old_path_dict = {}
flann = pyflann.FLANN()
IMAGE_DIR = 'UUID/'
SAVE_DIR = 'ImageRetrieval/'
LOG_PATH = '../web_logs/raw_log.txt'
topk = 5
delim = ';'


def load_exist_model():
    model_path = '../model'
    detect_model = os.path.join(model_path, 'detect/')
    face_model = os.path.join(model_path, 'facenet/20180617-003426/')
    feature_path = '../data/feature.txt'
    image_name_path = '../data/project.txt'   

    # create tensorflow session and load models
    config = tf.ConfigProto(allow_soft_placement=True)
    config.gpu_options.allow_growth = True
    global sess
    sess = tf.Session(config=config)
    facenet.load_model_in_sess(face_model,sess)
    global pnet, rnet, onet
    pnet, rnet, onet = detect_face.create_mtcnn(sess, detect_model)

    # load index params
    index = 'index'
    # load dataset
    fw = open(feature_path, 'r')
    lines = fw.readlines()
    rows = len(lines)
    cols = len(lines[0].split())
    dataset = np.zeros((rows, cols), dtype=np.float32)
    for i in range(len(lines)):
        dataset[i] = np.array(lines[i].split())
    fw.close()
    global flann
    flann.load_index(index, dataset)
    
    # load idx name dict
    fw = io.open(image_name_path, 'r', encoding='utf-8')
    lines = fw.readlines()
    for i in range(len(lines)):
        idx_path_dict[i] = lines[i].split()[0].encode('utf-8').decode('utf-8')
        new_old_path_dict[lines[i].split()[0].encode('utf-8').decode('utf-8')] = lines[i].split()[1].encode('utf-8').decode('utf-8')
    fw.close()
    print 'load ok'

app = Flask(__name__)
app.debug = True
@app.route('/retrival', methods=["POST", "GET"])
def retrival():
    input_str = str(request.values.get("input"))
    # input_str = input_s
    store_path = input_str.split(delim)[-1]
    image_paths = input_str.split(delim)[:-1]
    result_list = []
    global sess, pnet, rnet, onet, flann, IMAGE_DIR, LOG_PATH
    sw = open(LOG_PATH, 'a+')
    for i in range(len(image_paths)):
        img_path = IMAGE_DIR + image_paths[i]
        result = image_retrival.mainstream(sess, img_path, i, store_path, IMAGE_DIR, SAVE_DIR, pnet, rnet, onet, flann, topk)
        if result == False:
            continue
        result_list.append(result[0])
    if len(result_list) == 0:
        return False
    final_result_list = []
    if len(result_list) > 1:
        results = set(result_list[0][0]).intersection(*result_list[1:][0])
        print results
        print result_list
        if results == set([]):
            for part in result_list:
                name = idx_path_dict[part[0][0]].split('/')[1].encode('utf-8').decode('utf-8')
                final_result_list.append(name)
                new_path = idx_path_dict[part[0][0]]
                path = new_old_path_dict[new_path]
                sw.writelines(name.decode("utf-8").encode("utf-8") + " " + path.encode('utf-8').decode('utf-8') + " "
                    + str(time.time()) + "\n")
                final_result_list.append(path)
        else:
            for res in results:
                name = idx_path_dict[res].split('/')[1].encode('utf-8').decode('utf-8')
                final_result_list.append(name)
                new_path = idx_path_dict[res]
                path = new_old_path_dict[new_path]
                sw.writelines(name.decode("utf-8").encode("utf-8") + " " + path.encode('utf-8').decode('utf-8') + " "
                    + str(time.time()) + "\n")
                final_result_list.append(path)
    else:
        for res in result_list[0][0]:
            name = idx_path_dict[res].split('/')[1].encode('utf-8').decode('utf-8')
            final_result_list.append(name)
            new_path = idx_path_dict[res]
            path = new_old_path_dict[new_path]
            sw.writelines(name.decode("utf-8").encode("utf-8") + " " + path.encode('utf-8').decode('utf-8') + " "
                + str(time.time()) + "\n")
            final_result_list.append(path)
    # final_result_list = set(final_result_list)
    sw.close()
    final_result = ""
    for i in range(len(final_result_list)):
        final_result = final_result + final_result_list[i]
        if i % 2 == 0:
            final_result = final_result + ","
        else:
            final_result = final_result + ";"
    return final_result.decode("utf-8").encode("utf-8")

if __name__ == '__main__':
    load_exist_model()
    app.run(host='0.0.0.0', port=6175, debug=False)
