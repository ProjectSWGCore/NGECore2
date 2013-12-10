import sys

def setup(core, actor, buff): 
	core.buffService.addBuffToCreature(actor, 'bh_armor_sprint_1')
	core.skillModService.addSkillMod(actor, 'movement', 16)
	core.skillModService.addSkillMod(actor, 'movement_resist_snare', 100)

	
	##For Testing Purposes
	##actor.sendSystemMessage('You are running at ' + str(actor.getSpeedMultiplierBase()) + ' times the running speed.', 0)
	
	return
	
def removeBuff(core, actor, buff):
	core.buffService.removeBuffFromCreature(actor, 'bh_armor_sprint_1')
	core.skillModService.deductSkillMod(actor, 'movement', 16)
	core.skillModService.deductSkillMod(actor, 'movement_resist_snare', 100)

	return
