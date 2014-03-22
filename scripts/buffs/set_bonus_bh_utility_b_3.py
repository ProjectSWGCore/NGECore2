import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'fast_attack_line_dm_cc', 35)
	core.skillModService.addSkillMod(actor, 'bh_dire_root', 45)
	core.skillModService.addSkillMod(actor, 'bh_dire_snare', 45)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'fast_attack_line_dm_cc', 35)
	core.skillModService.deductSkillMod(actor, 'bh_dire_root', 45)
	core.skillModService.deductSkillMod(actor, 'bh_dire_snare', 45)
	return