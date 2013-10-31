import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'combat_strikethrough_value', 2)
	core.skillModService.addSkillMod(actor, 'expertise_critical_niche_all', 15)
	core.skillModService.addSkillMod(actor, 'expertise_strikethrough_chance', 2)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'combat_strikethrough_value', 2)
	core.skillModService.deductSkillMod(actor, 'expertise_critical_niche_all', 15)
	core.skillModService.deductSkillMod(actor, 'expertise_strikethrough_chance', 2)
	return