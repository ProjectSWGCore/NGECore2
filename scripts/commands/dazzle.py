import sys

def setup():
    return
    
def run(core, actor, target, commandString):
	
	command = 'Dazzle1'
	effect = 'clienteffect/entertainer_dazzle_level_1.cef'
	rLevel = 10 # minimum level to perform this effect
	
	if commandString == '2':
		command = 'Dazzle2'
		effect = 'clienteffect/entertainer_dazzle_level_2.cef'
		rLevel = 20
	
	elif commandString == '3':
		command = 'Dazzle3'
		effect = 'clienteffect/entertainer_dazzle_level_3.cef'
		rLevel = 30
	
	if actor.getLevel() < rLevel:
		actor.sendSystemMessage('@performance:effect_lack_skill_self')
		return
	
	if core.entertainmentService.performEffect(actor, command, effect, target) is True:
		actor.sendSystemMessage('@performance:effect_perform_dazzle', 0)
	return