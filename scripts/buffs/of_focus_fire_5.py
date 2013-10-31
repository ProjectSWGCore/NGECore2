import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'combat_strikethrough_value', 4)
	core.skillModService.addSkillMod(actor, 'expertise_critical_niche_all', 29)
	core.skillModService.addSkillMod(actor, 'expertise_strikethrough_chance', 4)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'combat_strikethrough_value', 4)
	core.skillModService.deductSkillMod(actor, 'expertise_critical_niche_all', 29)
	core.skillModService.deductSkillMod(actor, 'expertise_strikethrough_chance', 4)
	return