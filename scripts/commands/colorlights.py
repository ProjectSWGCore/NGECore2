import sys

def setup():
    return
    
def run(core, actor, target, commandString):
	
	command = 'ColorLights1'
	effect = 'entertainer_color_lights_level_1.cef'
	
	if actor.getLevel() < 4:
		actor.sendSystemMessage('@performance:effect_lack_skill_self', 0)
		return
	
	# TODO: Find out levels for other colored light 2 and 3
	
	if core.entertainmentService.performEffect(actor, command, effect, target) is True:
		actor.sendSystemMessage('@performance:effect_perform_color_lights', 0)
	return