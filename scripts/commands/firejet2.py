import sys

def setup():
    return
    
def run(core, actor, target, commandString):
	
	if actor.getLevel() < 76:
		actor.sendSystemMessage('@performance:effect_lack_skill_self', 0)
		return
	
	if core.entertainmentService.performEffect(actor, 'FireJetsB', 'clienteffect/entertainer_fire_jets2.cef', target) is True:
		actor.sendSystemMessage('@performance:effect_perform_fire_jets_2', 0)
	return