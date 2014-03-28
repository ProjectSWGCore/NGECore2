import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	actor.playEffectObject('appearance/pt_force_avoid_incapacitation.prt', 'fs_buff_def_1_1')
	core.skillModService.addSkillMod(actor, 'expertise_stance', 1)
	
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_stance', 1)
	return
	