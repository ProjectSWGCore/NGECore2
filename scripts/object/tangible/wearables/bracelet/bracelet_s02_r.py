import sys

def setup(core, object):
	return
	
def equip(core, actor, object):

	##Enforcer
	if object.getStfName() == ('item_bracelet_r_set_bh_dps_01_01'):
		core.skillModService.addSkillMod(actor, 'expertise_action_line_dm', 1)
		core.skillModService.addSkillMod(actor, 'expertise_action_line_dm_crit', 1)
		core.skillModService.addSkillMod(actor, 'expertise_buff_duration_line_bh_return_fire' , 1)
		core.skillModService.addSkillMod(actor, 'expertise_cooldown_line_dm' , 1)
		return
		
	##Frontman
	elif object.getStfName() == ('item_bracelet_r_set_commando_utility_a_01_01'):
		core.skillModService.addSkillMod(actor, 'constitution_modified', 20)
		core.skillModService.addSkillMod(actor, 'display_only_block' , 40)
		core.skillModService.addSkillMod(actor, 'combat_block_value' , 50)
		return
	return
	
def unequip(core, actor, object):

	##Enforcer
	if object.getStfName() == ('item_bracelet_r_set_bh_dps_01_01'):
		core.skillModService.deductSkillMod(actor, 'expertise_action_line_dm', 1)
		core.skillModService.deductSkillMod(actor, 'expertise_action_line_dm_crit', 1)
		core.skillModService.deductSkillMod(actor, 'expertise_buff_duration_line_bh_return_fire' , 1)
		core.skillModService.deductSkillMod(actor, 'expertise_cooldown_line_dm' , 1)
		return
		
		
	##Frontman
	elif object.getStfName() == ('item_bracelet_r_set_commando_utility_a_01_01'):
		core.skillModService.deductSkillMod(actor, 'constitution_modified', 20)
		core.skillModService.deductSkillMod(actor, 'display_only_block' , 40)
		core.skillModService.deductSkillMod(actor, 'combat_block_value' , 50)
		return
	return

