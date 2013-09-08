import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'bounty_hunter_1a':
		return

	actor.addSkill('expertise_bh_carbine_act_4')

	actor.addSkillMod('expertise_action_weapon_1', 5)
	actor.addSkillMod('expertise_action_weapon_2', 5)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'bounty_hunter_1a':
		return

	actor.removeSkill('expertise_bh_carbine_act_4')

	actor.removeSkillMod('expertise_action_weapon_1', 5)
	actor.removeSkillMod('expertise_action_weapon_2', 5)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
