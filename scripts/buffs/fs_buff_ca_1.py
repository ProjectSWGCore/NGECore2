import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	actor.playEffectObject('appearance/pt_sokan_focus.prt', 'fs_buff_ca_1')
	core.skillModService.addSkillMod(actor, 'expertise_focus', 1)
	
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_focus', 1)
	return
	