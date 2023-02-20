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
cap = cv.VideoCapture(0)
if not cap.isOpened():
    print("Cannot open camera")
    exit()
while True:
    # Capture frame-by-frame
    ret, frame = cap.read()
    # if frame is read correctly ret is True
    if not ret:
        print("Can't receive frame (stream end?). Exiting ...")
        break
    # Our operations on the frame come here

    # Display the resulting frame
    #cv.imshow('Video', frame)
    #if cv.waitKey(1) == ord('q'):
     #   break
# When everything done, release the capture
cap.release()
'''
