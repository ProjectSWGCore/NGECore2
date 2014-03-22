from protocol.swg import PlayClientEffectObjectMessage
import sys

def setup():
    return
    
def run(core, actor, target, commandString):

	command = 'SpotLight1'
	effect = 'clienteffect/entertainer_spot_light_level_1.cef'
	rLevel = 1 # minimum level to perform this effect
	
	if commandString == '2':
		command = 'SpotLight2'
		effect = 'clienteffect/entertainer_spot_light_level_2.cef'
		rLevel = 20
	
	elif commandString == '3':
		command = 'SpotLight3'
		effect = 'clienteffect/entertainer_spot_light_level_3.cef'
		rLevel = 30
	
	if actor.getLevel() < rLevel:
		actor.sendSystemMessage('@performance:effect_lack_skill_self')
		return

	if core.entertainmentService.performEffect(actor, command, effect, None) is True:
		actor.sendSystemMessage('@performance:effect_perform_spot_light', 0)
	return
