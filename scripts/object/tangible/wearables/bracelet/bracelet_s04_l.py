import sys

def setup(core, object):
	return
	
def equip(core, actor, object):

	##Direfate
	if object.getStfName() == ('item_bracelet_l_set_bh_utility_b_01_01'):
		core.skillModService.addSkillMod(actor, 'bh_dire_root', 1)
		core.skillModService.addSkillMod(actor, 'bh_dire_snare', 1)
		core.skillModService.addSkillMod(actor, 'fast_attack_line_dm_cc', 2)
		return
	return
	
def unequip(core, actor, object):

	##Direfate
	if object.getStfName() == ('item_bracelet_l_set_bh_utility_b_01_01'):
		core.skillModService.deductSkillMod(actor, 'bh_dire_root', 1)
		core.skillModService.deductSkillMod(actor, 'bh_dire_snare', 1)
		core.skillModService.deductSkillMod(actor, 'fast_attack_line_dm_cc', 2)
		return
	return

