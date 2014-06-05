import sys

def add(core, actor, skillMod, value):
	core.skillModService.addSkillMod(actor, "expertise_critical_1h", value)
	core.skillModService.addSkillMod(actor, "expertise_critical_2h", value)
	core.skillModService.addSkillMod(actor, "expertise_critical_unarmed", value)
	core.skillModService.addSkillMod(actor, "expertise_critical_polearm", value)
	return

def deduct(core, actor, skillMod, value):
	core.skillModService.deductSkillMod(actor, "expertise_critical_1h", value)
	core.skillModService.deductSkillMod(actor, "expertise_critical_2h", value)
	core.skillModService.deductSkillMod(actor, "expertise_critical_unarmed", value)
	core.skillModService.deductSkillMod(actor, "expertise_critical_polearm", value)
	return
	