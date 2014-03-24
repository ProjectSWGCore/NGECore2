import sys

def setup():
    return
    
def run(core, actor, target, commandString):
	
	command = 'Distract1'
	effect = 'clienteffect/entertainer_distract_level_1.cef'
	rLevel = 18 # minimum level to perform this effect
	
	if commandString == '2':
		command = 'Distract2'
		effect = 'clienteffect/entertainer_distract_level_2.cef'
		rLevel = 50
	
	elif commandString == '3':
		command = 'Distract3'
		effect = 'clienteffect/entertainer_distract_level_3.cef'
		rLevel = 75
	
	if actor.getLevel() < rLevel:
		actor.sendSystemMessage('@performance:effect_lack_skill_self')
		return
	
	if core.entertainmentService.performEffect(actor, command, effect, target) is True:
		actor.sendSystemMessage('@performance:effect_perform_distract', 0)
	return