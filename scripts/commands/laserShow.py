import sys

def setup():
    return
    
def run(core, actor, target, commandString):
	
	if actor.getLevel() < 42:
		actor.sendSystemMessage('@performance:effect_lack_skill_self', 0)
		return
	
	if core.entertainmentService.performEffect(actor, 'LaserShow', 'clienteffect/entertainer_laser_show.cef', target) is True:
		actor.sendSystemMessage('@performance:effect_perform_laser_show', 0)
	return
