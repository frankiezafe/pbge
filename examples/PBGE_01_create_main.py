from ProcessingBGE.ProcessingBGE import ProcessingBGE as pbge
import math
import mathutils
import random

# called once
def init():
    scene = bge.logic.getCurrentScene()
    scene.post_draw = [animate]
    pbge.configure()
    
    # basic 3D objects
    pbge.createPlane( -2,0,0 )
    pbge.createCube( 0,2,0 )
    pbge.createSphere( 0,-2,0 )
    pbge.createCylinder( 2,0,0 )
    
    #let's place a spot
    pbge.createSpot( 0,0,7 )

# called at each frame
def animate():
    pbge.update()

# always leave this at the bottom of the script!
if pbge.isconfigured() == False:
    init()
