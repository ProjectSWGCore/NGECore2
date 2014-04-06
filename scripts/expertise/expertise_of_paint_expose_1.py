import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 4:
		actor.addAbility("of_adv_paint_expose_1")
	if actor.getLevel() >= 12:
		actor.addAbility("of_adv_paint_expose_2")
	if actor.getLevel() >= 20:
		actor.addAbility("of_adv_paint_expose_3")
	if actor.getLevel() >= 30:
		actor.addAbility("of_adv_paint_expose_4")
	if actor.getLevel() >= 38:
		actor.addAbility("of_adv_paint_expose_5")
	if actor.getLevel() >= 52:
		actor.addAbility("of_adv_paint_expose_6")
	if actor.getLevel() >= 62:
		actor.addAbility("of_adv_paint_expose_7")
	if actor.getLevel() >= 72:
		actor.addAbility("of_adv_paint_expose_8")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("of_adv_paint_expose_1")
	actor.removeAbility("of_adv_paint_expose_2")
	actor.removeAbility("of_adv_paint_expose_3")
	actor.removeAbility("of_adv_paint_expose_4")
	actor.removeAbility("of_adv_paint_expose_5")
	actor.removeAbility("of_adv_paint_expose_6")
	actor.removeAbility("of_adv_paint_expose_7")
	actor.removeAbility("of_adv_paint_expose_8")
	return
