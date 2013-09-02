import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')
	
	if not player:
		return
		
	if not player.getProfession() == 'entertainer_1a':
		return
		
	actor.addSkill('expertise_en_inspired_resilience_2')
	
	actor.addSkillMod('expertise_en_inspire_resist_increase', 50)

	
	addAbilities(core, actor, player)

	return
	
def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')
	
	if not player:
		return
		
	if not player.getProfession() == 'entertainer_1a':
		return
		
	actor.removeSkill('expertise_en_inspired_resilience_2')
		
	actor.removeSkillMod('expertise_en_inspire_resist_increase', 50)
	
	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):

	return
	
def removeAbilities(core, actor, player):

	return