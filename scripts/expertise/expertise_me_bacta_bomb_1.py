import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')
	
	if not player:
		return
		
	if not player.getProfession() == 'medic_1a':
		return
		
	actor.addSkill('expertise_me_bacta_bomb_1')

	return
	
def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')
	
	if not player:
		return
		
	if not player.getProfession() == 'medic_1a':
		return
		
	actor.removeSkill('expertise_me_bacta_bomb_1')
	
	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):

	level = actor.getLevel()
	
	if level >=28:
		actor.addAbility('me_bacta_bomb_1')
	if level >=40:
		actor.addAbility('me_bacta_bomb_2')
	if level >=58:
		actor.addAbility('me_bacta_bomb_3')
	if level >=72:
		actor.addAbility('me_bacta_bomb_4')
	if level >=88:
		actor.addAbility('me_bacta_bomb_5')
	
	return
	
def removeAbilities(core, actor, player):

	actor.removeAbility('me_bacta_bomb_1')
	actor.removeAbility('me_bacta_bomb_2')
	actor.removeAbility('me_bacta_bomb_3')
	actor.removeAbility('me_bacta_bomb_4')
	actor.removeAbility('me_bacta_bomb_5')
	
	return