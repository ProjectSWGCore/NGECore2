import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'trader_struct_1a':
		return

	actor.addSkill('expertise_structure_shipwright_advanced_theory_1')

	actor.addSkillMod('chassis_experimentation', 10)
	actor.addSkillMod('weapon_systems_experimentation', 10)
	actor.addSkillMod('engine_experimentation', 10)
	actor.addSkillMod('booster_experimentation', 10)
	actor.addSkillMod('power_systems_experimentation', 10)
	actor.addSkillMod('shields_experimentation', 10)
	actor.addSkillMod('advanced_ship_experimentation', 10)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'trader_struct_1a':
		return

	actor.removeSkill('expertise_structure_shipwright_advanced_theory_1')

	actor.removeSkillMod('chassis_experimentation', 10)
	actor.removeSkillMod('weapon_systems_experimentation', 10)
	actor.removeSkillMod('engine_experimentation', 10)
	actor.removeSkillMod('booster_experimentation', 10)
	actor.removeSkillMod('power_systems_experimentation', 10)
	actor.removeSkillMod('shields_experimentation', 10)
	actor.removeSkillMod('advanced_ship_experimentation', 10)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
