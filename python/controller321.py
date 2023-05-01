import pygame
import socket
import threading

# Initialize Pygame
pygame.init()

# Initialize the Xbox X controller
pygame.joystick.init()

# Check for connected joysticks/gamepads
joystick_count = pygame.joystick.get_count()
if joystick_count == 0:
    print("No Xbox X controller found.")
    pygame.quit()
    exit()

# Select the first connected controller
controller = pygame.joystick.Joystick(0)
controller.init()

# Print the controller's name
print("Connected to controller:", controller.get_name())

# Initialize a socket for TCP communication
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# Specify the server's address and port
server_address = ('localhost', 12345)  # Replace with your server's address and port

# Bind the socket to the server's address and port
s.bind(server_address)

# Listen for incoming connections
s.listen(1)

# List of connected clients
clients = []

# Function to handle incoming connections
def handle_client(client_socket):
    while True:
        # Receive data from the client
        data = client_socket.recv(1024)
        if not data:
            clients.remove(client_socket)
            break

    client_socket.close()

# Start a thread that listens for incoming connections
def start_server():
    while True:
        print("Waiting for a connection...")
        client_socket, client_address = s.accept()
        print("Accepted connection from:", client_address)
        
        # Add the new client to the list of clients
        clients.append(client_socket)
        
        # Start a new thread to handle this client's communication
        client_thread = threading.Thread(target=handle_client, args=(client_socket,))
        client_thread.start()

# Start the server thread
server_thread = threading.Thread(target=start_server)
server_thread.start()

# Main game loop
running = True

message = ""

def send_message(message):
    for client in clients:
        client.send(message.encode())

while running:
    # Handle events
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False

        # Check for controller input events
        if event.type == pygame.JOYBUTTONDOWN:
            # Handle button press events
            if event.button == 0:
                message = "A"
            if event.button == 1:
                message = "B"
            if event.button == 3:
                message = "Y"

            if event.button == 2:  
                message = "X"
                running = False
                
            # Send the message to all connected clients
            send_message(message)

        elif event.type == pygame.JOYBUTTONUP:
            # Handle button release events
            if event.button == 0:
                message = "A released"
            if event.button == 1:
                message = "B released"
            if event.button == 3:
                message = "Y released"
                
            # Send the message to all connected clients
            send_message(message)

        elif event.type == pygame.JOYAXISMOTION:
            # Handle joystick motion events
            if event.axis == 0:
                # X-axis motion of the left joystick
                x_axis_left = event.value
                print("Left Joystick X-axis value:", x_axis_left)
                message = "LX: " + str(x_axis_left)

            elif event.axis == 1:
                # Y-axis motion of the left joystick
                y_axis_left = event.value
                message = "LY: " + str(y_axis_left)

            elif event.axis == 3:
                # X-axis motion of the right joystick
                x_axis_right = event.value
                message = "RX: " + str(x_axis_right)

            elif event.axis == 4:
                # Y-axis motion of the right joystick
                y_axis_right = event.value
                message = "RY: " + str(y_axis_right)
            send_message(message)
        if message != "":
            print(message)
            message = ""
# Close the socket
s.close()

# Quit Pygame
pygame.quit()
