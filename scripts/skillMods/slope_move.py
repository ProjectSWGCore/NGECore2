import sys

def add(core, actor, name, base):
	actor.addSkillMod(name, base)
	actor.setSpeedMultiplierBase(2.5 + (2.5 * (actor.getSkillModBase('expertise_movement_buff_fs_force_run')) / 100))
	return
	
def deduct(core, actor, name, base):
	actor.deductSkillMod(name, base)
	actor.setSpeedMultiplierBase(1)
	return
	