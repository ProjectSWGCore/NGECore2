import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 4:
		actor.addAbility("sp_hd_melee_0")
	if actor.getLevel() >= 12:
		actor.addAbility("sp_hd_melee_1")
	if actor.getLevel() >= 20:
		actor.addAbility("sp_hd_melee_2")
	if actor.getLevel() >= 30:
		actor.addAbility("sp_hd_melee_3")
	if actor.getLevel() >= 38:
		actor.addAbility("sp_hd_melee_4")
	if actor.getLevel() >= 52:
		actor.addAbility("sp_hd_melee_5")
	if actor.getLevel() >= 62:
		actor.addAbility("sp_hd_melee_6")
		
	if actor.getLevel() >= 4:
		actor.addAbility("sp_hd_range_0")
	if actor.getLevel() >= 12:
		actor.addAbility("sp_hd_range_1")
	if actor.getLevel() >= 20:
		actor.addAbility("sp_hd_range_2")	
	if actor.getLevel() >= 30:
		actor.addAbility("sp_hd_range_3")	
	if actor.getLevel() >= 38:
		actor.addAbility("sp_hd_range_4")
	if actor.getLevel() >= 52:
		actor.addAbility("sp_hd_range_5")	
	if actor.getLevel() >= 62:
		actor.addAbility("sp_hd_range_6")	
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("sp_hd_melee_0")
	actor.removeAbility("sp_hd_melee_1")
	actor.removeAbility("sp_hd_melee_2")
	actor.removeAbility("sp_hd_melee_3")
	actor.removeAbility("sp_hd_melee_4")
	actor.removeAbility("sp_hd_melee_5")
	actor.removeAbility("sp_hd_melee_6")
	
	actor.removeAbility("sp_hd_range_0")
	actor.removeAbility("sp_hd_range_1")
	actor.removeAbility("sp_hd_range_2")
	actor.removeAbility("sp_hd_range_3")
	actor.removeAbility("sp_hd_range_4")
	actor.removeAbility("sp_hd_range_5")
	actor.removeAbility("sp_hd_range_6")
	return
