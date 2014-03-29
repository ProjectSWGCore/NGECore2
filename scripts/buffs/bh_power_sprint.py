import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	actor.setSpeedMultiplierBase(2)
	core.skillModService.addSkillMod(actor, 'movement_resist_snare', 100)
	
	##For Testing Purposes
	##actor.sendSystemMessage('You are running at ' + str(actor.getSpeedMultiplierBase()) + ' times the running speed.', 0)
	
	return
	
def remove(core, actor, buff):
	actor.setSpeedMultiplierBase(1)
	core.skillModService.deductSkillMod(actor, 'movement_resist_snare', 100)
	return
