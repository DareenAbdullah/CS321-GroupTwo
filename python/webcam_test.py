'''
 webcam_test.py 
 Rekha Seethamraju

 An example to demonstrate the use of the WebCam library
 for PyBBIO.

 This example program is in the public domain.
'''

from bbio import *
from WebCam import WebCam

cam = WebCam()
s = 0

def setup():
  pass
  
def loop():
  global s
  location = "pic"+str(s)
  cam.takeSnapshot(location)
  print "saving image to %s" % location
  s += 1
  delay(10000)
  
run(setup, loop)
'''
# ON MAC, the following command needs to be ran
# sudo sysctl -w net.inet.udp.maxdgram=6553500
# This is because data sent is currently too big, my next goal is to find out how
# to reduce this to keep it under the MAX SIZE LIMIT and NOT have to run the above
# command
# it also currently runs endlessly

#!/usr/bin/env python3
import cv2 as cv
import imutils
import socket
import time

import numpy as np

#Opens socket
server_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
port = 12345
#Opens default camera
#otherwise, we should we be able to use cap.open(path) after VideoCapture() object is created
cap = cv.VideoCapture(0)
if not cap.isOpened():
	print("Cannot open camera")
	exit()
while True:
	while (cap.isOpened()):
		#Captures image
		ret, frame = cap.read()
		if not ret:
			print ("Can't receive frame. Exiting ...")
			break
		encoded, buffer = cv.imencode('.jpg', frame, [cv.IMWRITE_JPEG_QUALITY, 35])
		#Sends packet to an IP address, and port number
		server_socket.sendto(buffer.tobytes(), ('10.0.0.245', port))
'''
