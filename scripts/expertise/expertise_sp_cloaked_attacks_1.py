import sys

def addAbilities(core, actor, player):
	if actor.getLevel() >= 10:
		actor.addAbility("sp_stealth_melee_0")
	if actor.getLevel() >= 20:
		actor.addAbility("sp_stealth_melee_1")
	if actor.getLevel() >= 32:
		actor.addAbility("sp_stealth_melee_2")
	if actor.getLevel() >= 46:
		actor.addAbility("sp_stealth_melee_3")
	if actor.getLevel() >= 60:
		actor.addAbility("sp_stealth_melee_4")
	if actor.getLevel() >= 70:
		actor.addAbility("sp_stealth_melee_5")
	if actor.getLevel() >= 86:
		actor.addAbility("sp_stealth_melee_6")
		
	if actor.getLevel() >= 10:
		actor.addAbility("sp_stealth_ranged_0")
	if actor.getLevel() >= 20:
		actor.addAbility("sp_stealth_ranged_1")
	if actor.getLevel() >= 32:
		actor.addAbility("sp_stealth_ranged_2")	
	if actor.getLevel() >= 46:
		actor.addAbility("sp_stealth_ranged_3")	
	if actor.getLevel() >= 60:
		actor.addAbility("sp_stealth_ranged_4")
	if actor.getLevel() >= 70:
		actor.addAbility("sp_stealth_ranged_5")	
	if actor.getLevel() >= 86:
		actor.addAbility("sp_stealth_ranged_6")	
	return

def removeAbilities(core, actor, player):
	actor.removeAbility("sp_stealth_melee_0")
	actor.removeAbility("sp_stealth_melee_1")
	actor.removeAbility("sp_stealth_melee_2")
	actor.removeAbility("sp_stealth_melee_3")
	actor.removeAbility("sp_stealth_melee_4")
	actor.removeAbility("sp_stealth_melee_5")
	actor.removeAbility("sp_stealth_melee_6")
	
	actor.removeAbility("sp_stealth_ranged_0")
	actor.removeAbility("sp_stealth_ranged_1")
	actor.removeAbility("sp_stealth_ranged_2")
	actor.removeAbility("sp_stealth_ranged_3")
	actor.removeAbility("sp_stealth_ranged_4")
	actor.removeAbility("sp_stealth_ranged_5")
	actor.removeAbility("sp_stealth_ranged_6")
	return

