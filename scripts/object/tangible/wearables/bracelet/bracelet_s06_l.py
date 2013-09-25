import sys

def setup(core, object):
	return
	
def equip(core, actor, object):
	
	##Grenadier
	if object.getStfName() == ('item_bracelet_l_set_commando_dps_01_01'):
		core.skillModService.addSkillMod(actor, 'fast_attack_line_co_grenade', 3)
		core.skillModService.addSkillMod(actor, 'expertise_action_line_co_grenade' , 2)
		core.skillModService.addSkillMod(actor, 'expertise_freeshot_co_grenade' , 2)
		return
	return
	
def unequip(core, actor, object):

	##Grenadier
	if object.getStfName() == ('item_bracelet_l_set_commando_dps_01_01'):
		core.skillModService.deductSkillMod(actor, 'fast_attack_line_co_grenade', 3)
		core.skillModService.deductSkillMod(actor, 'expertise_action_line_co_grenade' , 2)
		core.skillModService.deductSkillMod(actor, 'expertise_freeshot_co_grenade' , 2)
		return
	return
