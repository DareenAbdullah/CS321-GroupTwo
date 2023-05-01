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
while running:
    # Handle events
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False

        # Check for controller input events
        if event.type == pygame.JOYBUTTONDOWN:
            # Handle button press events
            if event.button == 0:
                print("A button was pressed")
                message = "A button was pressed"
                for client in clients:
                    client.sendall(message.encode())
            # Handle other button events...

        elif event.type == pygame.JOYBUTTONUP:
            # Handle button release events
            # Handle these events...

        #elif event.type == pygame.JOYAXISMOTION:
            # Handle joystick motion events
            # Handle these events...
    

# Close the socket
s.close()

# Quit Pygame
pygame.quit()
