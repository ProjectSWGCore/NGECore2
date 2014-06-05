import sys

def add(core, actor, skillMod, value):
	core.skillModService.addSkillMod(actor, 'display_only_strikethrough', 100 * skillMod.getBase())
	return

def deduct(core, actor, skillMod, value):
	core.skillModService.deductSkillMod(actor, 'display_only_strikethrough', 100 * skillMod.getBase())
	return
	
