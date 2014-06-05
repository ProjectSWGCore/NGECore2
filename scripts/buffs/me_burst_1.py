import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_critical_niche_all', 3)
	core.skillModService.addSkillMod(actor, 'strikethrough_vulnerable', 5)
	core.skillModService.addSkillMod(actor, 'critical_hit_vulnerable', 5)
	return
	
def remove(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_critical_niche_all', 3)
	core.skillModService.deductSkillMod(actor, 'strikethrough_vulnerable', 5)
	core.skillModService.deductSkillMod(actor, 'critical_hit_vulnerable', 5)
	return