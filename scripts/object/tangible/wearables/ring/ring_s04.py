import sys

def setup(core, object):
	return
	
def equip(core, actor, object):

	
	##Heroism
	if object.getStfName() == ('item_ring_set_hero_01_01'):
		core.skillModService.addSkillMod(actor, 'strength_modified', 30)
		core.skillModService.addSkillMod(actor, 'precision_modified', 30)
		core.skillModService.addSkillMod(actor, 'luck_modified', 30)
		return
		
	##Flawless
	elif object.getStfName() == ('item_ring_a_set_bh_utility_a'):
		core.skillModService.addSkillMod(actor, 'precision_modified', 50)
		core.skillModService.addSkillMod(actor, 'luck_modified', 30)
		return
		
	##Grenadier
	elif object.getStfName() == ('item_ring_a_set_commando_dps'):
		core.skillModService.addSkillMod(actor, 'fast_attack_line_co_grenade', 3)
		core.skillModService.addSkillMod(actor, 'expertise_action_line_co_grenade' , 2)
		core.skillModService.addSkillMod(actor, 'expertise_freeshot_co_grenade' , 2)
		return
		
	##Juggernaut
	elif object.getStfName() == ('item_ring_a_set_commando_utility_b'):
		core.skillModService.addSkillMod(actor, 'constitution_modified', 10)
		core.skillModService.addSkillMod(actor, 'strength_modified' , 15)
		core.skillModService.addSkillMod(actor, 'expertise_devastation_bonus' , 5)
		return
	return
	
def unequip(core, actor, object):

	##Heroism
	if object.getStfName() == ('item_ring_set_hero_01_01'):
		core.skillModService.deductSkillMod(actor, 'strength_modified', 30)
		core.skillModService.deductSkillMod(actor, 'precision_modified', 30)
		core.skillModService.deductSkillMod(actor, 'luck_modified', 30)
		return
		
	##Flawless
	elif object.getStfName() == ('item_ring_a_set_bh_utility_a'):
		core.skillModService.deductSkillMod(actor, 'precision_modified', 50)
		core.skillModService.deductSkillMod(actor, 'luck_modified', 30)
		return
		
	##Grenadier
	elif object.getStfName() == ('item_ring_a_set_commando_dps'):
		core.skillModService.deductSkillMod(actor, 'fast_attack_line_co_grenade', 3)
		core.skillModService.deductSkillMod(actor, 'expertise_action_line_co_grenade' , 2)
		core.skillModService.deductSkillMod(actor, 'expertise_freeshot_co_grenade' , 2)
		return
		
	##Juggernaut
	elif object.getStfName() == ('item_ring_a_set_commando_utility_b'):
		core.skillModService.deductSkillMod(actor, 'constitution_modified', 10)
		core.skillModService.deductSkillMod(actor, 'strength_modified' , 15)
		core.skillModService.deductSkillMod(actor, 'expertise_devastation_bonus' , 5)
		return
	return
		