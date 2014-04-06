import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 34:
		actor.addAbility("sm_pistol_whip_1")
	if actor.getLevel() >= 50:
		actor.addAbility("sm_pistol_whip_2")
	if actor.getLevel() >= 66:
		actor.addAbility("sm_pistol_whip_3")
	if actor.getLevel() >= 82:
		actor.addAbility("sm_pistol_whip_4")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("sm_pistol_whip_1")
	actor.removeAbility("sm_pistol_whip_2")
	actor.removeAbility("sm_pistol_whip_3")
	actor.removeAbility("sm_pistol_whip_4")
	return
