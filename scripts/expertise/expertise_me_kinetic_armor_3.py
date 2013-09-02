import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')
	
	if not player:
		return
		
	if not player.getProfession() == 'medic_1a':
		return
		
	actor.addSkill('expertise_me_kinetic_armor_3')
	
	actor.addSkillMod('kinetic', 225)
	actor.addSkillMod('energy', 225)
	actor.addSkillMod('acid', 225)
	actor.addSkillMod('heat', 225)
	actor.addSkillMod('cold', 225)
	actor.addSkillMod('electricity', 225))
	
	addAbilities(core, actor, player)

	return
	
def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')
	
	if not player:
		return
		
	if not player.getProfession() == 'medic_1a':
		return
		
	actor.removeSkill('expertise_me_kinetic_armor_3')
		
	actor.removeSkillMod('kinetic', 225)
	actor.removeSkillMod('energy', 225)
	actor.removeSkillMod('acid', 225)
	actor.removeSkillMod('heat', 225)
	actor.removeSkillMod('cold', 225)
	actor.removeSkillMod('electricity', 225)
	
	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):

	return
	
def removeAbilities(core, actor, player):

	return