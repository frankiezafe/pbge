from ProcessingBGE.ProcessingBGE import ProcessingBGE as pbge
import math
import mathutils
import random

# called once
def init():
    scene = bge.logic.getCurrentScene()
    scene.post_draw = [animate]
    pbge.configure()
    
    # storing the reference of the created objects
    # into 'pbge' to animate them later
    pbge.myplane = pbge.createPlane( 0,0,-3 )
    pbge.mycube = pbge.createCube( 0,2,0 )
    pbge.mysphere = pbge.createSphere( 0,-2,0 )
    pbge.mycylinder = pbge.createCylinder( 2,0,0 )
    pbge.myspot = pbge.createSpot( 0,0,7 )
    
    pbge.scale( pbge.myplane, 10 )
    pbge.scale( pbge.mycylinder, 0.3, 0.3, 4 )
    pbge.scaleZ( pbge.mycube, 2 )
    

# called at each frame
def animate():
    pbge.update()
    
    # and rotation of objects
    pbge.rotateZ( pbge.myplane, 0.001 )
    pbge.rotateX( pbge.mycube, 0.01 )
    pbge.rotateY( pbge.mysphere, 0.02 )
    pbge.rotateY( pbge.mycylinder, 0.03 )
    
# always leave this at the bottom of the script!
if pbge.isconfigured() == False:
    init()
