#!/usr/bin/env python3
import Adafruit_BBIO as PWM

ESC_MOTOR = 'P9_16'
#100% forward throttle is 5% cycle
FULL_THROTTLE = 5

#100% reverse is 10% cycle
REVERSE_THROTTLE = 10

#Starting position
RESTING_POSITION = 7.5

class CarMov:
    def __init__(self):
        self.current_throttle = RESTING_POSITION
        PWM.start(ESC_MOTOR, 0 , 50)
        PWM.set_duty_cycle(ESC_MOTOR, RESTING_POSITION)
    
    def move_forward(self):
        if self.current_throttle > FULL_THROTTLE:
            self.current_throttle -= 0.5
            PWM.set_duty_cycle(ESC_MOTOR, self.current_throttle)
        return current_throttle
    
    def move_backward(self):
        if self.current_throttle < REVERSE_THROTTLE:
            self.current_throttle += 0.5
            PWM.set_duty_cycle(ESC_MOTOR, self.current_throttle)
        return current_throttle
    
    def reset_throttle(self):
        while self.current_throttle > RESTING_POSITION:
            self.current_throttle -= 0.5
            PWM.set_duty_cycle(ESC_MOTOR, self.current_throttle)
            
#TODO: find a way to reset throttle position once key presses are relesed.
