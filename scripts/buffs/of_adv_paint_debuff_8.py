import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'critical_damage_vulnerability', 10)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'critical_damage_vulnerability', 10)
	return
	