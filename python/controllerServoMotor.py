import pygame
import socket
import Adafruit_BBIO.PWM as PWM


# set up the controller
pygame.init()
joystick = pygame.joystick.Joystick(0)
joystick.init()
 
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

# set up the socket connection
host = "xxx"  # IP address of the device
port = "xxx"  # port number to use for the socket connection
sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

# Set up the servo for steering
servo_pin = "P9_14"  # pin to use for the servo
PWM.start(servo_pin, 0, 50)
servo_angle = 90  # initial angle of the servo

# Set up the motor control
motor_pin = "P8_13"  # pin to use for motor control
PWM.start(motor_pin, 0, 1000)

# Function to change servo angle
def change_servo_angle(angle):
    global servo_angle
    servo_angle = angle
    PWM.set_duty_cycle(servo_pin, servo_angle)

# Function to send motor speed and servo angle over the network
def send_message(speed, angle):
    message = f"{speed},{angle}"
    sock.sendto(message.encode(), (host, port))


# Main game loop
while True:
    # Handle events
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            pygame.quit()
            exit()

        # Check for controller input events
        if event.type == pygame.JOYBUTTONDOWN:

            if event.button == 2:  
                print("X button was pressed, exiting program")
                running = False

        elif event.type == pygame.JOYAXISMOTION:
            # Handle joystick motion events
            if event.axis == 0:
                # X-axis motion of the left joystick (servo control)
                print("Left joystick x axis is " + x_axis)
                x_axis = event.value
                angle = int((x_axis + 1) * 90)  # Map joystick value (-1 to 1) to servo angle (0 to 180)
                change_servo_angle(angle)

            elif event.axis == 4:
                # Y-axis motion of the right joystick (motor control)
                y_axis = event.value
                speed = int((y_axis + 1) * 50)  # Map joystick value (-1 to 1) to motor speed (0 to 100)
                PWM.set_duty_cycle(motor_pin, speed)
                send_message(speed, servo_angle)
