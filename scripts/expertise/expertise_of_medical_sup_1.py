import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 18:
		actor.addAbility("of_medical_sup_1")
	if actor.getLevel() >= 30:
		actor.addAbility("of_medical_sup_2")
	if actor.getLevel() >= 44:
		actor.addAbility("of_medical_sup_3")
	if actor.getLevel() >= 58:
		actor.addAbility("of_medical_sup_4")
	if actor.getLevel() >= 72:
		actor.addAbility("of_medical_sup_5")
	if actor.getLevel() >= 86:
		actor.addAbility("of_medical_sup_6")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("of_medical_sup_1")
	actor.removeAbility("of_medical_sup_2")
	actor.removeAbility("of_medical_sup_3")
	actor.removeAbility("of_medical_sup_4")
	actor.removeAbility("of_medical_sup_5")
	actor.removeAbility("of_medical_sup_6")
	return
