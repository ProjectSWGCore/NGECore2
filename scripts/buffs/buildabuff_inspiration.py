from resources.common import BuffBuilder
import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
    
    buffWorkshop = actor.getAttachment('buffWorkshop')
    
    if buffWorkshop is None:
        return
    
    attached = actor.getAttachment('inspireDuration')
    print (attached)
    buff.setDuration(float(actor.getAttachment('inspireDuration') * 60))
    print ('Buff Duration: ' + str(actor.getAttachment('inspireDuration')))
    for BuffItem in buffWorkshop:
        core.skillModService.addSkillMod(actor, BuffItem.getSkillName(), BuffItem.getAffectAmount())
        print ('Gave skill mod ' + BuffItem.getSkillName() + ' with affect of ' + str(BuffItem.getAffectAmount()))

    return
    
def remove(core, actor, buff):
    for BuffItem in actor.getAttachment('buffWorkshop'):
        core.skillModService.deductSkillMod(actor, BuffItem.getSkillName(), BuffItem.getAffectAmount())
    
    actor.setAttachment('buffWorkshop', 'none')
    return