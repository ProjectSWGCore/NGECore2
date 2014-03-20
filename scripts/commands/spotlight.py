from protocol.swg import PlayClientEffectObjectMessage
import sys

def setup():
    return
    
def run(core, actor, target, commandString):

	command = 'SpotLight1'
	effect = 'clienteffect/entertainer_spot_light_level_1.cef'
	
	# TODO: Figure out what levels the spotlight effects upgraded at.
	
	if actor.getLevel() >= 80:
		command == 'SpotLight3'
		effect = 'clienteffect/entertainer_spot_light_level_3.cef'
	
	elif actor.getLevel() >= 40:
		command == 'SpotLight2'
		effect = 'clienteffect/entertainer_spot_light_level_2.cef'
		
	if core.entertainmentService.performEffect(actor, command, effect, None) is True:
		actor.sendSystemMessage('@performance:effect_perform_spot_light', 0)
	return
