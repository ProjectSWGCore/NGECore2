import sys

def setup(core, actor, buff):
    return
    
def run(core, actor, target, commandString):
    print (' shuttle called!')
    lambdaShuttle = stcSvc.spawnObject('object/creature/npc/theme_park/shared_event_lambda_shuttle.iff', actor.getPlanet().getName(), 
                                       long(0), actor.getPosition().x, actor.getPosition().y, actor.getPosition().z, actor.getOrientation().y, actor.getOrientation().w)
    
    lambdaShuttle.setPosture(0) # 0 or 2?
    print ('posture set!')
    return