import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 26:
		actor.addAbility("fs_ae_dm_cc_1")
	if actor.getLevel() >= 34:
		actor.addAbility("fs_ae_dm_cc_2")
	if actor.getLevel() >= 48:
		actor.addAbility("fs_ae_dm_cc_3")
	if actor.getLevel() >= 62:
		actor.addAbility("fs_ae_dm_cc_4")
	if actor.getLevel() >= 76:
		actor.addAbility("fs_ae_dm_cc_5")
	if actor.getLevel() >= 90:
		actor.addAbility("fs_ae_dm_cc_6")
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("fs_ae_dm_cc_1")
	actor.removeAbility("fs_ae_dm_cc_2")
	actor.removeAbility("fs_ae_dm_cc_3")
	actor.removeAbility("fs_ae_dm_cc_4")
	actor.removeAbility("fs_ae_dm_cc_5")
	actor.removeAbility("fs_ae_dm_cc_6")
	return
