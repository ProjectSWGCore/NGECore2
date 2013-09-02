import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')
	
	if not player:
		return
		
	if not player.getProfession() == 'medic_1a':
		return
		
	actor.addSkill('expertise_me_bacta_grenade_1')

	return
	
def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')
	
	if not player:
		return
		
	if not player.getProfession() == 'medic_1a':
		return
		
	actor.removeSkill('expertise_me_bacta_grenade_1')
	
	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):
	
	level = actor.getLevel()
	
	if level >=24:
		actor.addAbility('me_bacta_grenade_1')
		
	if level >=36:
		actor.addAbility('me_bacta_grenade_2')
		
	if level >=48:
		actor.addAbility('me_bacta_grenade_3')
		
	if level >=66:
		actor.addAbility('me_bacta_grenade_4')
		
	if level >=84:
		actor.addAbility('me_bacta_grenade_5')
	
	return
	
def removeAbilities(core, actor, player):
	
	actor.removeAbility('me_bacta_grenade_1')
	actor.removeAbility('me_bacta_grenade_2')
	actor.removeAbility('me_bacta_grenade_3')
	actor.removeAbility('me_bacta_grenade_4')
	actor.removeAbility('me_bacta_grenade_5')
	
	return