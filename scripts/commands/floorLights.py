import sys

def setup():
    return
    
def run(core, actor, target, commandString):
	
	if actor.getLevel() < 82:
		actor.sendSystemMessage('@performance:effect_lack_skill_self', 0)
		return
	
	if core.entertainmentService.performEffect(actor, 'DanceFloor', 'clienteffect/entertainer_dance_floor.cef', target) is True:
		actor.sendSystemMessage('@performance:effect_perform_dance_floor', 0)
	return