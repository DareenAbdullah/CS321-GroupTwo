import pygame

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
            if event.button == 1:
                print("B button was pressed")
            if event.button == 3:
                print("Y button was pressed")

            if event.button == 2:  
                print("X button was pressed, exiting program")
                running = False

        elif event.type == pygame.JOYBUTTONUP:
            # Handle button release events
            if event.button == 0:
                print("A button was released")
            if event.button == 1:
                print("B button was released")
            if event.button == 3:
                print("Y button was released")

        elif event.type == pygame.JOYAXISMOTION:
            # Handle joystick motion events
            if event.axis == 0:
                # X-axis motion of the left joystick
                x_axis_left = event.value
                print("Left Joystick X-axis value:", x_axis_left)

            elif event.axis == 1:
                # Y-axis motion of the left joystick
                y_axis_left = event.value
                print("Left Joystick Y-axis value:", y_axis_left)

            elif event.axis == 3:
                # X-axis motion of the right joystick
                x_axis_right = event.value
                print("Right Joystick X-axis value:", x_axis_right)

            elif event.axis == 4:
                # Y-axis motion of the right joystick
                y_axis_right = event.value
                print("Right Joystick Y-axis value:", y_axis_right)

# Quit Pygame
pygame.quit()


