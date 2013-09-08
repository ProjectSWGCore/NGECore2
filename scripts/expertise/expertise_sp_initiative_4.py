import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'spy_1a':
		return

	actor.addSkill('expertise_sp_initiative_4')

	actor.addSkillMod('expertise_critical_line_sp_stealth_melee', 15)
	actor.addSkillMod('expertise_critical_line_sp_stealth_ranged', 15)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'spy_1a':
		return

	actor.removeSkill('expertise_sp_initiative_4')

	actor.removeSkillMod('expertise_critical_line_sp_stealth_melee', 15)
	actor.removeSkillMod('expertise_critical_line_sp_stealth_ranged', 15)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
