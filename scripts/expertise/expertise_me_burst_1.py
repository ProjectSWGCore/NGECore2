import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')
	
	if not player:
		return
		
	if not player.getProfession() == 'medic_1a':
		return
		
	actor.addSkill('expertise_me_burst_1')

	return
	
def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')
	
	if not player:
		return
		
	if not player.getProfession() == 'medic_1a':
		return
		
	actor.removeSkill('expertise_me_burst_1')
	
	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):
	
	level = actor.getLevel()
	
	if level >=16:
		actor.addAbility('me_burst_1')
		
	if level >=28:
		actor.addAbility('me_burst_2')
		
	if level >=46:
		actor.addAbility('me_burst_3')
		
	if level >=64:
		actor.addAbility('me_burst_4')
		
	if level >=72:
		actor.addAbility('me_burst_5')
	
	return
	
def removeAbilities(core, actor, player):
	
	actor.removeAbility('me_burst_1')
	actor.removeAbility('me_burst_2')
	actor.removeAbility('me_burst_3')
	actor.removeAbility('me_burst_4')
	actor.removeAbility('me_burst_5')
	
	return