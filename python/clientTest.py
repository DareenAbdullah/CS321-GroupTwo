#!/usr/bin/env python3

import socket
import codecs

HOST = "127.0.0.1"
PORT = 12345
num = 0
with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
	s.bind((HOST, PORT))
	s.listen()
	connection, address = s.accept()
	with connection:
		print(f"Connected by {address}")
		while True:
			data = connection.recv(1024)
			if not data:
				continue
			data = str(data, 'utf-8')
			if "q" in data:
				break
			elif "none" in data:
				print(data)
				connection.sendall(bytes('none\n', 'utf-8'))
				continue

			elif "0" in data:
				connection.sendall(bytes('0\n', 'utf-8'))
				print("Running moveLeft()")
				continue
			elif "1" in data:
				connection.sendall(bytes('1\n', 'utf-8'))
				print("Running moveRight()")
				continue
			elif "2" in data:
				connection.sendall(bytes('2\n', 'utf-8'))
				print("Running moveForward()")
				continue
			elif "3" in data:
				connection.sendall(bytes('3\n', 'utf-8'))
				print("Running moveInReverse()")
				continue
			elif "4" in data:
				connection.sendall(bytes('Not streaming\n', 'utf-8'))
				print("currently unable to stream")
				continue
			elif "5" in data:
				connection.sendall(bytes('Not streaming\n'), 'utf-8')
				print("Not streaming")
				continue
			else:
				connection.sendall(bytes("data\n"), 'utf-8')
				continue

print("Connection successful from beaglebone")