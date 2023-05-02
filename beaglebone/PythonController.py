#!/usr/bin/env python3

import socket
import codecs
import threading
import Steering
import CarMov

#HOST = "192.168.8.229"
HOST = "192.168.8.220"
PORT = 12346

#thread1 will handle video streaming
thread1 = None

steer = Steering.Steering()
#thread2 will handle steering
thread2 = None

car_throttle = CarMov.CarMov()
#thread3 will handle acceleration
thread3 = None

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
	s.connect((HOST, PORT))
	s.sendall(b"Hello from Racer2")
	print('worked')
	while True:
		data = s.recv(1024)
		if not data:
			continue
		data = data.decode('utf-8')
		data = data.split()
		#if A, B, Y , or X were pressed
		if len(data) < 2:
			if 'X' in str(data[0]):
				break
			continue
		else:
			if 'L' in data[0][0] or 'R' in data[0][0]:
				#Makes the second argumetn (axix value) numeric
				try:
					axis = float(data[1][:8])
				except:
					axis = float(data[1][:3])
				if axis == 0:
					if 'L' in data[0][0]:
						steer.resetCycle()
						print(data[0] + str(axis))
					if 'R' in data[0][0]:
						car_throttle.reset_throttle()
						print(data[0] + str(axis))
				else:
					if 'LX:' in data[0] or 'LY' in data[0]:
						if axis < 0:
							steer.setCycleLeft()
						else:
							steer.setCycleRight()
					else:
						if axis < 0:
							car_throttle.move_forward()
							print(data[0] + str(axis))
						else:
							car_throttle.move_backward()
							print(data[0] + str(axis))
				
			
car_throttle.reset_throttle()
print("Connection successful from beaglebone")
