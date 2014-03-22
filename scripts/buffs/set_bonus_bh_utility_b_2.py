import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'fast_attack_line_dm_cc', 10)
	core.skillModService.addSkillMod(actor, 'bh_dire_root', 20)
	core.skillModService.addSkillMod(actor, 'bh_dire_snare', 20)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'fast_attack_line_dm_cc', 10)
	core.skillModService.deductSkillMod(actor, 'bh_dire_root', 20)
	core.skillModService.deductSkillMod(actor, 'bh_dire_snare', 20)
	return