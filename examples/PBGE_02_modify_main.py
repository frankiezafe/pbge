from ProcessingBGE.ProcessingBGE import ProcessingBGE as pbge
import math
import mathutils
import random

# called once
def init():
    scene = bge.logic.getCurrentScene()
    scene.post_draw = [animate]
    pbge.configure()
    
    # getting the reference of the created objects
    # for further modifications
    myplane = pbge.createPlane( -2,0,0 )
    mycube = pbge.createCube( 0,2,0 )
    mysphere = pbge.createSphere( 0,-2,0 )
    mycylinder = pbge.createCylinder( 2,0,0 )
    
    #let's place a spot
    myspot = pbge.createSpot( 0,0,7 )
    
    # all objects are created, 
    # let's change the position:
    pbge.moveTo( myplane, 0,0,-3 )
    # let's change the size:
    pbge.scale( myplane, 10 )
    pbge.scaleZ( mycube, 3 )
    # let's change the orientation:
    pbge.rotateX( mycylinder, 15 )
    

# called at each frame
def animate():
    pbge.update()

# always leave this at the bottom of the script!
if pbge.isconfigured() == False:
    init()
