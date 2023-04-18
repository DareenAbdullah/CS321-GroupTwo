#!/usr/bin/env python3
import Adafruit_BBIO.PWM as PWM

class Steering:
    #Initialize pins, start servo, and set cycle limits
    def __init__(self):
        self.servoPin = 'P9_14'
        self.currentPosition = 5
        PWM.start(self.servoPin, 5, 50)
    #Moves servo left
    def setCycleLeft(self):
        if self.currentPosition > 5:
            self.currentPosition -= 1
            PWM.set_duty_cycle(self.servoPin, self.currentPosition)
    #Moves servo right 
    def setCycleRight(self):
        if self.currentPosition < 10:
            self.currentPosition += 1
            PWM.set_duty_cycle(self.servoPin, self.currentPosition)
