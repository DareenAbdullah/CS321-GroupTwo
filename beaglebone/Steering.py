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
        PWM.start(SERVO, DEFAULT_POSITION, 50)
    
    def setCycleLeft(self):
        if self.currentPosition > FULL_LEFT_POS:
            self.currentPosition -= 0.5
            PWM.set_duty_cycle(SERVO, self.currentPosition)
        
    def setCycleRight(self):
        if self.currentPosition < FULL_RIGHT_POS:
            self.currentPosition += 0.5
            PWM.set_duty_cycle(SERVO, self.currentPosition)
    def resetCycle(self):
        while self.currentPosition < DEFAULT_POSITION:
            self.currentPosition += 0.5
            PWM.set_duty_cycle(SERVO, self.currentPosition)
        
        while self.currentPosition > DEFAULT_POSITION:
            self.currentPosition -= 0.5
            PWM.set_duty_cycle(SERVO, self.currentPosition)
