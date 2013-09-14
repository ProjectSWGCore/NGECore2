import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'trader_struct_1a':
		return

	actor.addSkill('expertise_structure_shipwright_dexterity_1')

	actor.addSkillMod('chassis_assembly', 5)
	actor.addSkillMod('engine_assembly', 5)
	actor.addSkillMod('booster_assembly', 5)
	actor.addSkillMod('power_systems', 5)
	actor.addSkillMod('weapon_systems', 5)
	actor.addSkillMod('shields_assembly', 5)
	actor.addSkillMod('advanced_assembly', 5)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'trader_struct_1a':
		return

	actor.removeSkill('expertise_structure_shipwright_dexterity_1')

	actor.removeSkillMod('chassis_assembly', 5)
	actor.removeSkillMod('engine_assembly', 5)
	actor.removeSkillMod('booster_assembly', 5)
	actor.removeSkillMod('power_systems', 5)
	actor.removeSkillMod('weapon_systems', 5)
	actor.removeSkillMod('shields_assembly', 5)
	actor.removeSkillMod('advanced_assembly', 5)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
