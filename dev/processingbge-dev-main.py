# TO RUN : PUT YOUR MOUSE IN THE 3D VIEW AND PRESS 'p' 
from ProcessingBGE.ProcessingBGE import ProcessingBGE as pbge
import math
import mathutils
import random

# called once
def init():
    scene = bge.logic.getCurrentScene()
    scene.post_draw = [animate]
    pbge.configure()
    
    p = pbge.createPlane( 0,0,-2 )
    pbge.scale( p, 10,10,1 )
    
    pbge.createOscReceiver( 23000 )

# called at each frame
def animate():
    pbge.update()


# always leave this at the bottom of the script!
if pbge.isconfigured() == False:
    init()