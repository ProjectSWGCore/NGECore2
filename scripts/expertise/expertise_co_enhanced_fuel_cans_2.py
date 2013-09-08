import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'commando_1a':
		return

	actor.addSkill('expertise_co_enhanced_fuel_cans_2')

	actor.addSkillMod('expertise_action_weapon_12', 5)
	actor.addSkillMod('expertise_action_weapon_13', 5)
	actor.addSkillMod('commando_passive_dot', 1)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'commando_1a':
		return

	actor.removeSkill('expertise_co_enhanced_fuel_cans_2')

	actor.removeSkillMod('expertise_action_weapon_12', 5)
	actor.removeSkillMod('expertise_action_weapon_13', 5)
	actor.removeSkillMod('commando_passive_dot', 1)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
