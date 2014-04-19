import sys

def setup():
    return
    
def run(core, actor, target, commandString):
	
	if actor.getLevel() < 34:
		actor.sendSystemMessage('@performance:effect_lack_skill_self', 0)
		return
	
	if core.entertainmentService.performEffect(actor, 'ColorSwirl', 'clienteffect/entertainer_color_swirl.cef', target) is True:
		actor.sendSystemMessage('@performance:effect_perform_color_swirl', 0)
	return