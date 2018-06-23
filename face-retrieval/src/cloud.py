#coding=utf-8
import io
import wordcloud
import cv2
import time
import sys
reload(sys)
sys.setdefaultencoding('utf-8')

LOG_PATH = '/data2/an_zhao/lab/ir_project/web_logs/raw_log.txt'
NEW_LOG_PATH = '/data2/an_zhao/lab/ir_project/web_logs/new_log.txt'
CLOUD_PATH = '/data2/an_zhao/lab/tomcat/apache-tomcat-8.5.27/webapps/ROOT/ImageRetrieval/cloud.png' 
name_count = {}
name_time = {}
name_path = {}

def rewrite_log():
    global name_count, name_time
    fw = io.open(LOG_PATH, 'r', encoding='utf-8')
    lines = fw.readlines()
    for line in lines:
        name = line.split()[0].encode('utf-8').decode('utf-8')
        path = line.split()[1].encode('utf-8').decode('utf-8')
        timestamp = float(line.split()[2])
        if name not in name_count.keys():
            name_count[name] = 1
        else:
            name_count[name] += 1
        if name not in name_time.keys():
            name_time[name] = float(timestamp)
        else:
            if float(timestamp) > name_time[name]:
                name_time[name] = float(timestamp)
        name_path[name] = path
    fw.close()
    global sort_list
    sort_list = sorted(name_count.items(), key = lambda item: item[1])
    sort_list.reverse()
    sw = io.open(NEW_LOG_PATH, "w+", encoding='utf-8')
    for tp in sort_list:
    	name = tp[0]
    	num = tp[1]
    	timestamp = name_time[name]
    	path = name_path[name]
    	sw.writelines(name + "," + path + "," + str(num) + "," + str(time.strftime('%Y-%m-%d %H:%M',time.localtime(timestamp))) + "\n")
    sw.close()

def generate_cloud():
	mask_path = 'mask.jpg'
	font_path = '/usr/share/fonts/opentype/noto/NotoSansCJK-Bold.ttc'
	mask = cv2.imread(mask_path)
	wc = wordcloud.WordCloud(background_color="white",
							max_words=30,
							mask=mask,
							font_path=font_path,
							max_font_size=100)
	global name_count
	wc.generate_from_frequencies(name_count)
	wc.to_file(CLOUD_PATH)
	print "ok"

if __name__ == '__main__':
	rewrite_log()
	generate_cloud()
