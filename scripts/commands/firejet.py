import sys

def setup():
    return
    
def run(core, actor, target, commandString):
	
	command = 'FireJets1'
	effect = 'entertainer_fire_jets_level_1.cef'
	
	if actor.getLevel() < 26:
		actor.sendSystemMessage('@performance:effect_lack_skill_self')
		return
	
	# TODO: Find out levels for other firejets 2 and 3
	
	if core.entertainmentService.performEffect(actor, command, effect, target) is True:
		actor.sendSystemMessage('@performance:effect_perform_fire_jets', 0)
	return