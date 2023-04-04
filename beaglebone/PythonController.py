'''
This is the main class communicating across all other classes in bb, starting threads as needed.
'''
#!/usr/bin/env python3

import socket
import codecs
import VideoStream
import threading

HOST = "127.0.0.1"
PORT = 12345
num = 0
streamer = VideoStream.VideoStream()
#thread1 will handle video streaming
thread1 = None

#thread2 will handle steering
thread2 = None

#thread3 will handle acceleration
thread3 = None
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
				streamer.stop_stream()
				break
			elif "none" in data:
				connection.sendall(bytes('none\n', 'utf-8'))

			elif "0" in data:
				connection.sendall(bytes('0\n', 'utf-8'))
				print("Running moveLeft()")
				
			elif "1" in data:
				connection.sendall(bytes('1\n', 'utf-8'))
				print("Running moveRight()")
				
			elif "2" in data:
				connection.sendall(bytes('2\n', 'utf-8'))
				print("Running moveForward()")
				
			elif "3" in data:
				connection.sendall(bytes('3\n', 'utf-8'))
				print("Running moveInReverse()")
				
			elif "4" in data:
				if streamer.is_streaming():
					connection.sendall(bytes('Streaming\n', 'utf-8'))
					print('streamer is streaming')
				else:
					print('streamer is NOT streaming')
					streamer.setup_stream()
					thread1 = threading.Thread(target=streamer.start_stream, args=(socket.gethostname(), 12346))
					thread1.start()
					connection.sendall(bytes('Streaming\n', 'utf-8'))
			elif "5" in data:
				print('here!')
				if streamer.is_streaming():
					streamer.stop_stream()
					connection.sendall(bytes('Not streaming\n', 'utf-8'))
				else:	
					connection.sendall(bytes('Not streaming\n', 'utf-8'))
				
			else:
				connection.sendall(bytes("\n", 'utf-8'))
				

print("Connection successful from beaglebone")
