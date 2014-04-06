from resources.common import BuffBuilder
import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
    
    buffWorkshop = actor.getAttachment('buffWorkshop')
    
    if buffWorkshop is None:
        return
    
    attached = actor.getAttachment('inspireDuration')

    buff.setDuration(float(actor.getAttachment('inspireDuration') * 60))

    for BuffItem in buffWorkshop:
        core.skillModService.addSkillMod(actor, BuffItem.getSkillName(), BuffItem.getAffectAmount())

    return
    
def remove(core, actor, buff):
	if actor.getAttachment('buffWorkshop') is not None:
		
		for BuffItem in actor.getAttachment('buffWorkshop'):
			core.skillModService.deductSkillMod(actor, BuffItem.getSkillName(), BuffItem.getAffectAmount())
		
		actor.setAttachment('buffWorkshop', None)
	return