from resources.common import BuffBuilder
import sys

def setup(core, actor, buff):
    
    buffWorkshop = actor.getAttachment('buffWorkshop')
    
    if buffWorkshop is None:
        return
    
    buff.setDuration(3600) #1 hour
    
    for BuffItem in buffWorkshop:
        core.skillModService.addSkillMod(actor, BuffItem.getSkillName(), BuffItem.getAffectAmount())

    return
    
def removeBuff(core, actor, buff):
    for BuffItem in actor.getAttachment('buffWorkshop'):
        core.skillModService.deductSkillMod(actor, BuffItem.getSkillName(), BuffItem.getAffectAmount())
    
    actor.setAttachment('buffWorkshop', 'none')
    return