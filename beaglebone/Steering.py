#!/usr/bin/env python3
import Adafruit_BBIO.PWM as PWM

SERVO = 'P9_14'
DEFAULT_POSITION = 7.5
FULL_LEFT_POS = 5
FULL_RIGHT_POS = 10

class Steering:
    #Initialize pins, start servo, and set cycle limits
    def __init__(self):
        self.currentPosition = DEFAULT_POSITION
        PWM.start(self.servoPin, DEFAULT_POSITION, 50)
    
    def setCycleLeft(self):
        if self.currentPosition > FULL_LEFT_POS:
            self.currentPosition -= 0.5
            PWM.set_duty_cycle(self.servoPin, self.currentPosition)
        
    def setCycleRight(self):
        if self.currentPosition < FULL_RIGHT_POS:
            self.currentPosition += 0.5
            PWM.set_duty_cycle(self.servoPin, self.currentPosition)

#TODO: find a way to reset position of the wheels when direction keys are released.
