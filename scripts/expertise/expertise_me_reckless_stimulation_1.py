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
	
	if level >=18:
		actor.addAbility('me_reckless_stimulation_1')
		
	if level >=30:
		actor.addAbility('me_reckless_stimulation_2')
		
	if level >=42:
		actor.addAbility('me_reckless_stimulation_3')
		
	if level >=58:
		actor.addAbility('me_reckless_stimulation_4')
		
	if level >=74:
		actor.addAbility('me_reckless_stimulation_5')
	
	if level >=86:
		actor.addAbility('me_reckless_stimulation_6')
	
	return
	
def removeAbilities(core, actor, player):
	
	actor.removeAbility('me_reckless_stimulation_1')
	actor.removeAbility('me_reckless_stimulation_2')
	actor.removeAbility('me_reckless_stimulation_3')
	actor.removeAbility('me_reckless_stimulation_4')
	actor.removeAbility('me_reckless_stimulation_5')
	actor.removeAbility('me_reckless_stimulation_6')
	
	return