import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')
	
	if not player:
		return
		
	if not player.getProfession() == 'medic_1a':
		return
		
	actor.addSkill('expertise_me_reckless_stimulation_1')

	return
	
def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')
	
	if not player:
		return
		
	if not player.getProfession() == 'medic_1a':
		return
		
	actor.removeSkill('expertise_me_reckless_stimulation_1')
	
	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):
	
	level = actor.getLevel()
	
	if level >=26:
		actor.addAbility('me_traumatize_1')
		
	if level >=38:
		actor.addAbility('me_traumatize_2')
		
	if level >=56:	
		actor.addAbility('me_traumatize_3')
		
	if level >=74:
		actor.addAbility('me_traumatize_4')
		
	if level >=88:
		actor.addAbility('me_traumatize_5')
	
	return
	
def removeAbilities(core, actor, player):
	
	actor.removeAbility('me_traumatize_1')
	actor.removeAbility('me_traumatize_2')
	actor.removeAbility('me_traumatize_3')
	actor.removeAbility('me_traumatize_4')
	actor.removeAbility('me_traumatize_5')
	
	return