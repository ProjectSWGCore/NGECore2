import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'medic_1a':
		return

	actor.addSkill('expertise_me_agility_1')

	actor.addSkillMod('agility_modified', 25)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'medic_1a':
		return

	actor.removeSkill('expertise_me_agility_1')

	actor.removeSkillMod('agility_modified', 25)

	removeAbilities(core, actor, player)

	return

def addAbilities(core, actor, player):
	if actor.getLevel() == 10:
		actor.addAbility("me_enhance_agility_1")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("me_enhance_agility_1")
	return
