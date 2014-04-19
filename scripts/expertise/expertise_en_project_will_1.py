import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 18:
		actor.addAbility("en_project_will_0")
	if actor.getLevel() >= 30:
		actor.addAbility("en_project_will_1")
	if actor.getLevel() >= 42:
		actor.addAbility("en_project_will_2")
	if actor.getLevel() >= 54:
		actor.addAbility("en_project_will_3")
	if actor.getLevel() >= 66:
		actor.addAbility("en_project_will_4")
	if actor.getLevel() >= 78:
		actor.addAbility("en_project_will_5")
	if actor.getLevel() >= 90:
		actor.addAbility("en_project_will_6")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("en_project_will_0")
	actor.removeAbility("en_project_will_1")
	actor.removeAbility("en_project_will_2")
	actor.removeAbility("en_project_will_3")
	actor.removeAbility("en_project_will_4")
	actor.removeAbility("en_project_will_5")
	actor.removeAbility("en_project_will_6")
	return
