import sys

def setup():
    return
    
def run(core, actor, target, commandString):
	level = actor.getLevel()
	command = 'SmokeBomb1'
	effect = 'clienteffect/entertainer_smoke_bomb_level_1.cef'
	rLevel = 50 # minimum level to perform this effect
	
	if commandString is None or commandString == '':
		if level >= 90:
			command = 'SmokeBomb3'
			effect = 'clienteffect/entertainer_smoke_bomb_level_3.cef'
		
		elif level >= 75:
			command = 'SmokeBomb2'
			effect = 'clienteffect/entertainer_smoke_bomb_level_2.cef'
	
	if commandString is not None:
		if commandString == '2':
			command = 'SmokeBomb2'
			effect = 'clienteffect/entertainer_smoke_bomb_level_2.cef'
			rLevel = 75
			
		elif commandString == '3':
			command = 'SmokeBomb3'
			effect = 'clienteffect/entertainer_smoke_bomb_level_3.cef'
			rLevel = 90
	
	if level < rLevel:
		actor.sendSystemMessage('@performance:effect_lack_skill_self', 0)
		return
	
	if core.entertainmentService.performEffect(actor, command, effect, target) is True:
		actor.sendSystemMessage('@performance:effect_perform_smoke_bomb', 0)
	return