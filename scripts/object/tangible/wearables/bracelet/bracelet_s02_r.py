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
	return
	
def unequip(core, actor, object):

	##Enforcer
	if object.getStfName() == ('item_bracelet_r_set_bh_dps_01_01'):
		core.skillModService.deductSkillMod(actor, 'expertise_action_line_dm', 1)
		core.skillModService.deductSkillMod(actor, 'expertise_action_line_dm_crit', 1)
		core.skillModService.deductSkillMod(actor, 'expertise_buff_duration_line_bh_return_fire' , 1)
		core.skillModService.deductSkillMod(actor, 'expertise_cooldown_line_dm' , 1)
		return
	return

