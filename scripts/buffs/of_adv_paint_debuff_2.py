import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'critical_damage_vulnerability', 4)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'critical_damage_vulnerability', 4)
	return
	