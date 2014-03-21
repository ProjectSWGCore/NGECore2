import sys

def setup():
    return
    
def run(core, actor, target, commandString):
	
	command = 'Distract1'
	effect = 'entertainer_distract_level_1.cef'
	
	if actor.getLevel() < 18:
		actor.sendSystemMessage('@performance:effect_lack_skill_self')
		return
	
	# TODO: Find out levels for other distract 2 and 3
	
	if core.entertainmentService.performEffect(actor, command, effect, target) is True:
		actor.sendSystemMessage('@performance:effect_perform_distract', 0)
	return