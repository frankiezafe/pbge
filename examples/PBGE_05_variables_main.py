from ProcessingBGE.ProcessingBGE import ProcessingBGE as pbge
import math
import mathutils
import random

# called once
def init():
    scene = bge.logic.getCurrentScene()
    scene.post_draw = [animate]
    pbge.configure()
    
    # changing background color
    pbge.background( 0,0,0 )
    
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
    
    # creation of a custom variable
    pbge.color = 0
    pbge.way = 1
    
# called at each frame
def animate():
    pbge.update()
    
    pbge.color += pbge.way
    # inverting way once the color is 0 or 255
    if ( pbge.color == 0 or pbge.color == 255 ):
        pbge.way *= -1
    
    pbge.changeColor( pbge.mycube, pbge.color,0,0 )
    pbge.moveTo( pbge.mysphere, 0,0, pbge.color / 127 )
    
# always leave this at the bottom of the script!
if pbge.isconfigured() == False:
    init()
