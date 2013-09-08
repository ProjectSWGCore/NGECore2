import sys

def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'trader_dom_1a':
		return

	actor.addSkill('expertise_domestic_chef_insight_1')

	actor.addSkillMod('expertise_complexity_decrease_chef', 1)

	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'trader_dom_1a':
		return

	actor.removeSkill('expertise_domestic_chef_insight_1')

	actor.removeSkillMod('expertise_complexity_decrease_chef', 1)

	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):


	return

def removeAbilities(core, actor, player):


	return
