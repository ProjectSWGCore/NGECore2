import sys

def add(core, actor, skillMod, value):
	core.skillModService.addSkillMod(actor, 'display_only_expertise_critical_hit_reduction', skillMod.getBase())
	return

def deduct(core, actor, skillMod, value):
	core.skillModService.deductSkillMod(actor, 'display_only_expertise_critical_hit_reduction', skillMod.getBase())
	return
	