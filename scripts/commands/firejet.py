import sys

def setup():
    return
    
def run(core, actor, target, commandString):
	level = actor.getLevel()
	command = 'FireJets1'
	effect = 'clienteffect/entertainer_fire_jets_level_1.cef'
	rLevel = 26 # minimum level to perform this effect
	
	if commandString is None or commandString == '':
		if level >= 75:
			command = 'FireJets3'
			effect = 'clienteffect/entertainer_fire_jets_level_3.cef'
		
		elif level >= 50:
			command = 'FireJets2'
			effect = 'clienteffect/entertainer_fire_jets_level_2.cef'
	
	if commandString is not None:
		if commandString == '2':
			command = 'FireJets2'
			effect = 'clienteffect/entertainer_fire_jets_level_2.cef'
			rLevel = 50
			
		elif commandString == '3':
			command = 'FireJets3'
			effect = 'clienteffect/entertainer_fire_jets_level_3.cef'
			rLevel = 75
	
	if level < rLevel:
		actor.sendSystemMessage('@performance:effect_lack_skill_self', 0)
		return
	
	if core.entertainmentService.performEffect(actor, command, effect, target) is True:
		actor.sendSystemMessage('@performance:effect_perform_fire_jets', 0)
	return