import sys

def setup():
    return
    
def run(core, actor, target, commandString):
	
	command = 'ColorLights1'
	effect = 'clienteffect/entertainer_color_lights_level_1.cef'
	rLevel = 4 # minimum level to perform this effect
	
	if commandString == '2':
		command = 'ColorLights2'
		effect = 'clienteffect/entertainer_color_lights_level_2.cef'
		rLevel = 20
	
	elif commandString == '3':
		command = 'ColorLights3'
		effect = 'clienteffect/entertainer_color_lights_level_3.cef'
		rLevel = 50
	
	if actor.getLevel() < rLevel:
		actor.sendSystemMessage('@performance:effect_lack_skill_self')
	
	if core.entertainmentService.performEffect(actor, command, effect, target) is True:
		actor.sendSystemMessage('@performance:effect_perform_color_lights', 0)
	return