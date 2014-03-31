import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'combat_divide_damage_taken', 1)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'combat_divide_damage_taken', 1)
	return 