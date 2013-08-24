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
    pbge.mycube = pbge.createCube( 0,0,0 )
    pbge.myspot = pbge.createSpot( 0,0,7 )
    pbge.scale( pbge.myplane, 10 )
    
    pbge.speed = 0.07
    
# called at each frame
def animate():
    pbge.update()
    
    # keyboard interactions
    
    # use 'f' for faster and 's' for slower
    # done only once when the key is pressed
    if ( pbge.keyPressed( 'f' ) ):
        pbge.speed *= 2
        pbge.moveTo( pbge.myspot, 0, 0, 7 + pbge.speed * 10 )

    if ( pbge.keyPressed( 's' ) ):
        pbge.speed *= 0.5
        pbge.moveTo( pbge.myspot, 0, 0, 7 + pbge.speed * 10 )
    
    # use 'r' to reset the cube position
    if ( pbge.keyPressed( 'r' ) ):
        pbge.moveTo( pbge.mycube, 0, 0, 0 )
    
    # use the arrows to move the cube
    if ( pbge.keyActive( pbge.ARROW_UP ) ):
        pbge.move( pbge.mycube, 0, pbge.speed, 0 )
    
    if ( pbge.keyActive( pbge.ARROW_DOWN ) ):
        pbge.move( pbge.mycube, 0, -pbge.speed, 0 )
        
    if ( pbge.keyActive( pbge.ARROW_LEFT ) ):
        pbge.move( pbge.mycube, -pbge.speed, 0, 0 )
    
    if ( pbge.keyActive( pbge.ARROW_RIGHT ) ):
        pbge.move( pbge.mycube, pbge.speed, 0, 0 )
    
    
# always leave this at the bottom of the script!
if pbge.isconfigured() == False:
    init()
