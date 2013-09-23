import sys

def setup(core, object):
	return
	
def equip(core, actor, object):

	
	##Heroism
	if object.getStfName() == ('item_bracelet_r_set_hero_01_01'):
		core.skillModService.addSkillMod(actor, 'strength_modified', 30)
		core.skillModService.addSkillMod(actor, 'precision_modified', 30)
		core.skillModService.addSkillMod(actor, 'luck_modified', 30)
		return
		
	##Flawless
	elif object.getStfName() == ('item_bracelet_r_set_bh_utility_a_01_01'):
		core.skillModService.addSkillMod(actor, 'precision_modified', 50)
		core.skillModService.addSkillMod(actor, 'luck_modified', 30)
		return
	return
	
def unequip(core, actor, object):

	##Heroism
	if object.getStfName() == ('item_bracelet_r_set_hero_01_01'):
		core.skillModService.deductSkillMod(actor, 'strength_modified', 30)
		core.skillModService.deductSkillMod(actor, 'precision_modified', 30)
		core.skillModService.deductSkillMod(actor, 'luck_modified', 30)
		return
		
	##Flawless
	elif object.getStfName() == ('item_bracelet_r_set_bh_utility_a_01_01'):
		core.skillModService.deductSkillMod(actor, 'precision_modified', 50)
		core.skillModService.deductSkillMod(actor, 'luck_modified', 30)
		return
	return