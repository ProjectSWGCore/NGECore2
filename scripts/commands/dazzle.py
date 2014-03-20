import sys

def setup():
    return
    
def run(core, actor, target, commandString):
	
	command = 'Dazzle1'
	effect = 'entertainer_dazzle_level_1.cef'
	
	if actor.getLevel() < 10:
		actor.sendSystemMessage('@performance:effect_lack_skill_self')
		return
	
	# TODO: Find out levels for other dazzle 2 and 3
	
	if core.entertainmentService.performEffect(actor, command, effect, target) is True:
		actor.sendSystemMessage('@performance:effect_perform_dazzle', 0)
	return