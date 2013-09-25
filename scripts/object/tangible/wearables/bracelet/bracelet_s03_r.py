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
		
	##Juggernaut
	elif object.getStfName() == ('item_bracelet_r_set_commando_utility_b_01_01'):
		core.skillModService.addSkillMod(actor, 'constitution_modified', 10)
		core.skillModService.addSkillMod(actor, 'strength_modified' , 15)
		core.skillModService.addSkillMod(actor, 'expertise_devastation_bonus' , 5)
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
		
	##Juggernaut
	elif object.getStfName() == ('item_bracelet_r_set_commando_utility_b_01_01'):
		core.skillModService.deductSkillMod(actor, 'constitution_modified', 10)
		core.skillModService.deductSkillMod(actor, 'strength_modified' , 15)
		core.skillModService.deductSkillMod(actor, 'expertise_devastation_bonus' , 5)
		return
	return