import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')
	
	if not player:
		return
		
	if not player.getProfession() == 'commando_1a':
		return
		
	actor.addSkill('expertise_co_pinpoint_shielding_4')
	
	actor.addSkillMod('energy', 250)
	actor.addSkillMod('kinetic', 250)
	actor.addSkillMod('acid', 250)
	actor.addSkillMod('heat', 250)
	actor.addSkillMod('cold', 250)
	actor.addSkillMod('electricity', 250)
	
	addAbilities(core, actor, player)

	return
	
def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')
	
	if not player:
		return
		
	if not player.getProfession() == 'commando_1a':
		return
		
	actor.removeSkill('expertise_co_pinpoint_shielding_4')
		
	actor.removeSkillMod('energy', 250)
	actor.removeSkillMod('kinetic', 250)
	actor.removeSkillMod('acid', 250)
	actor.removeSkillMod('heat', 250)
	actor.removeSkillMod('cold', 250)
	actor.removeSkillMod('electricity', 250)
	
	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):

	return
	
def removeAbilities(core, actor, player):

	return
	