import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')
	
	if not player:
		return
		
	if not player.getProfession() == 'commando_1a':
		return
		
	actor.addSkill('expertise_co_deflective_armor_3')
	
	actor.addSkillMod('damage_decrease_percentage', 2)
	
	addAbilities(core, actor, player)

	return
	
def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')
	
	if not player:
		return
		
	if not player.getProfession() == 'commando_1a':
		return
		
	actor.removeSkill('expertise_co_deflective_armor_3')
		
	actor.removeSkillMod('damage_decrease_percentage', 2)
	
	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):

	return
	
def removeAbilities(core, actor, player):

	return
	