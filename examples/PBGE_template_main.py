from ProcessingBGE.ProcessingBGE import ProcessingBGE as pbge
import math
import mathutils
import random

# called once
def init():
    scene = bge.logic.getCurrentScene()
    scene.post_draw = [animate]
    pbge.configure()

# called at each frame
def animate():
    pbge.update()

# always leave this at the bottom of the script!
if pbge.isconfigured() == False:
    init()
