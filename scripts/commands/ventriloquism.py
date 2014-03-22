import sys

def setup():
    return
    
def run(core, actor, target, commandString):
	
	command = 'Ventriloquism1'
	effect = 'clienteffect/entertainer_ventriloquism_level_1.cef'
	rLevel = 58 # minimum level to perform this effect
	
	if commandString == '2':
		command = 'Ventriloquism2'
		effect = 'clienteffect/entertainer_ventriloquism_level_2.cef'
		rLevel = 75
	
	elif commandString == '3':
		command = 'Ventriloquism3'
		effect = 'clienteffect/entertainer_ventriloquism_level_3.cef'
		rLevel = 90
	
	if actor.getLevel() < rLevel:
		actor.sendSystemMessage('@performance:effect_lack_skill_self')
		return
	
	if core.entertainmentService.performEffect(actor, command, effect, target) is True:
		actor.sendSystemMessage('@performance:effect_perform_ventriloquism', 0)
	return