import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')
	
	if not player:
		return
		
	if not player.getProfession() == 'commando_1a':
		return
		
	actor.addSkill('expertise_co_tibanna_gas_1')
	
	actor.addSkillMod('kill_meter_co_armor_splash_proc', 1)
	actor.addSkillMod('expertise_dot_duration_line_co_armor_splash', 2)
	
	addAbilities(core, actor, player)

	return
	
def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')
	
	if not player:
		return
		
	if not player.getProfession() == 'commando_1a':
		return
		
	actor.removeSkill('expertise_co_tibanna_gas_1')
	
	actor.removeSkillMod('kill_meter_co_armor_splash_proc', 1)
	actor.removeSkillMod('expertise_dot_duration_line_co_armor_splash', 2)
		
	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):

	return
	
def removeAbilities(core, actor, player):

	return
	