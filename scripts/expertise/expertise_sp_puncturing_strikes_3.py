import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'spy_1a':
		return

	actor.addSkill('expertise_sp_puncturing_strikes_3')

	actor.addSkillMod('expertise_damage_line_sp_stealth_melee', 10)
	actor.addSkillMod('expertise_damage_line_sp_hd_melee', 10)
	actor.addSkillMod('expertise_damage_line_sp_stealth_ranged', 10)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'spy_1a':
		return

	actor.removeSkill('expertise_sp_puncturing_strikes_3')

	actor.removeSkillMod('expertise_damage_line_sp_stealth_melee', 10)
	actor.removeSkillMod('expertise_damage_line_sp_hd_melee', 10)
	actor.removeSkillMod('expertise_damage_line_sp_stealth_ranged', 10)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
