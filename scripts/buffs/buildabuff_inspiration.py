from resources.common import BuffBuilder
import sys

def setup(core, actor, buff):
    
    buffWorkshop = actor.getAttachment('buffWorkshop')
    
    if buffWorkshop is None:
        return
    
    for BuffBuilder in buffWorkshop:
        core.skillModService.addSkillMod(actor, BuffBuilder.getStatAffects(), BuffBuilder.getTotalAffected())
        print (' gave the skill mod ' + BuffBuilder.getStatAffects() + ' with affect of ' + str(BuffBuilder.getTotalAffected()))
        return
    
    return
    
def removeBuff(core, actor, buff):
    for BuffBuilder in actor.getAttachment('buffWorkshop'):
        core.skillModService.deductSkillMod(actor, BuffBuilder.getStatAffects(), BuffBuilder.getTotalAffected())
        print ('removed skill mod!')
        return
    
    actor.setAttachment('buffWorkshop', 'none')
    return