import sys

def setup():
    return
    
def run(core, actor, target, commandString):
	
	command = 'Ventriloquism1'
	effect = 'entertainer_ventriloquism_level_1.cef'
	
	if actor.getLevel() < 58:
		actor.sendSystemMessage('@performance:effect_lack_skill_self')
		return
	
	# TODO: Find out levels for other ventriloquism 2 and 3
	
	if core.entertainmentService.performEffect(actor, command, effect, target) is True:
		actor.sendSystemMessage('@performance:effect_perform_ventriloquism', 0)
	return