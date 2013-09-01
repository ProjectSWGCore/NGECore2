import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')
	
	if not player:
		return
		
	if not player.getProfession() == 'commando_1a':
		return
		
	actor.addSkill('expertise_co_imp_stand_fast_1')
	
	actor.addSkillMod('expertise_cooldown_line_co_stand_fast', 120)
	actor.addSkillMod('expertise_buff_duration_line_co_stand_fast', 2)
	actor.addSkillMod('expertise_damage_decrease_percentage', 5)
	
	addAbilities(core, actor, player)

	return
	
def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')
	
	if not player:
		return
		
	if not player.getProfession() == 'commando_1a':
		return
		
	actor.removeSkill('expertise_co_imp_stand_fast_1')
	
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
	