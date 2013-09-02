import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')
	
	if not player:
		return
		
	if not player.getProfession() == 'medic_1a':
		return
		
	actor.addSkill('expertise_me_agility_2')
	
	actor.addSkillMod('agility_modified', 25)

	
	addAbilities(core, actor, player)

	return
	
def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')
	
	if not player:
		return
		
	if not player.getProfession() == 'medic_1a':
		return
		
	actor.removeSkill('expertise_me_agility_2')
		
	actor.removeSkillMod('agility_modified', 25)
	
	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):

	return
	
def removeAbilities(core, actor, player):

	return