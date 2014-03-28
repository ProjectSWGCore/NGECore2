import sys

def setup():
    return
    
def run(core, actor, target, commandString):
	
	if actor.getLevel() < 66:
		actor.sendSystemMessage('@performance:effect_lack_skill_self', 0)
		return
	
	if core.entertainmentService.performEffect(actor, 'CenterStage', 'clienteffect/entertainer_center_stage.cef', target) is True:
		actor.sendSystemMessage('@performance:effect_perform_center_stage', 0)
	return