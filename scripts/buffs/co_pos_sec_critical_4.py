import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_critical_hit_reduction', 20)
	core.skillModService.addSkillMod(actor, 'expertise_critical_niche_all', 8)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_critical_hit_reduction', 20)
	core.skillModService.deductSkillMod(actor, 'expertise_critical_niche_all', 8)
	return