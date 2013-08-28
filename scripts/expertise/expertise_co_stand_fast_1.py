import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')
	
	if not player:
		return
		
	if not player.getProfession() == 'commando_1a':
		return
		
	actor.addSkill('expertise_co_stand_fast_1')
	
	addAbilities(core, actor, player)

	return
	
def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')
	
	if not player:
		return
		
	if not player.getProfession() == 'commando_1a':
		return
		
	actor.removeSkill('expertise_co_stand_fast_1')
		
	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):

	actor.addAbility('co_stand_fast')

	return
	
def removeAbilities(core, actor, player):

	actor.removeAbility('co_stand_fast')

	return
	