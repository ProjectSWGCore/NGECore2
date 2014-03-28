import sys

def setup():
    return
    
def run(core, actor, target, commandString):
	
	if actor.getLevel() < 90:
		actor.sendSystemMessage('@performance:effect_lack_skill_self', 0)
		return
	
	if core.entertainmentService.performEffect(actor, 'FeaturedSolo', 'clienteffect/entertainer_featured_solo.cef', target) is True:
		actor.sendSystemMessage('@performance:effect_perform_featured_solo', 0)
	return