import sys

def setup():
    return
    
def run(core, actor, target, commandString):
	
	command = 'SmokeBomb1'
	effect = 'entertainer_smoke_bomb_level_1.cef'
	
	if actor.getLevel() < 50:
		actor.sendSystemMessage('@performance:effect_lack_skill_self', 0)
		return
	
	# TODO: Find out levels for other smoke bomb 2 and 3
	
	if core.entertainmentService.performEffect(actor, command, effect, target) is True:
		actor.sendSystemMessage('@performance:effect_perform_smoke_bomb', 0)
	return