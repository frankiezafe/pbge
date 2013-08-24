from ProcessingBGE.ProcessingBGE import ProcessingBGE as pbge
import math
import mathutils
import random

# called once
def init():
    scene = bge.logic.getCurrentScene()
    scene.post_draw = [animate]
    pbge.configure()
    
    pbge.myplane = pbge.createPlane( 0,0,-3 )
    pbge.mycube = pbge.createCube( 0,2,0 )
    pbge.myspot = pbge.createSpot( 0,0,7 )
    pbge.scale( pbge.myplane, 10 )
    
# called at each frame
def animate():
    pbge.update()
    
    # using the XY mouse position to move the cube
    mx = -5 + pbge.mouseX * 10
    my = 5 - pbge.mouseY * 10
    pbge.moveTo( pbge.mycube, mx, my, 0 )
    pbge.moveTo( pbge.myspot, mx * 0.3, my * 0.3, 7 )
    
    
# always leave this at the bottom of the script!
if pbge.isconfigured() == False:
    init()
