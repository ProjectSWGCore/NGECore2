import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 10:
		actor.addAbility("me_enhance_action_1")
	if actor.getLevel() >= 34:
		actor.addAbility("me_enhance_action_2")
	if actor.getLevel() >= 62:
		actor.addAbility("me_enhance_action_3")
		
	if actor.getLevel() >= 48:
		actor.addAbility("me_buff_health_2")
	if actor.getLevel() >= 76:
		actor.addAbility("me_buff_health_3")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("me_enhance_action_1")
	actor.removeAbility("me_enhance_action_2")
	actor.removeAbility("me_enhance_action_3")

	actor.removeAbility("me_buff_health_2")
	actor.removeAbility("me_buff_health_3")
	return
