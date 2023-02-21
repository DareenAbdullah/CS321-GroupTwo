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
#!/usr/bin/env python3
import cv2 as cv
import imutils
import socket
import time
import base64
import numpy as np

BUFF_SIZE = 65536

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

		encoded, buffer = cv.imencode('.jpg', frame, [cv.IMWRITE_JPEG_QUALITY, 80])

		#Sends packet to an IP address, and port number
		server_socket.sendto(buffer.tobytes(), (socket.gethostname(), port))

#closes webcam		
cap.release()
'''
