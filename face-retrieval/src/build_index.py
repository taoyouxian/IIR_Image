import numpy as np
import pyflann
feature_path = '../data/feature_0617.txt'
save_path = 'index'
# load dataset
fw = open(feature_path, 'r')
lines = fw.readlines()
rows = len(lines)
cols = len(lines[0].split())
dataset = np.zeros((rows, cols), dtype=np.float32)
for i in range(len(lines)):
	dataset[i] = np.array(lines[i].split())
fw.close()

flann = pyflann.FLANN()
flann.build_index(dataset, algorithm='kdtree', trees=4)
flann.save_index(save_path)