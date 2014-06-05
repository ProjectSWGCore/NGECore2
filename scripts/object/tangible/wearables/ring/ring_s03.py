import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'ring/unity')
	object.setAttachment('objType', 'ring')
	return
	
def equip(core, actor, object):

	##Direfate
	if object.getStfName() == ('item_ring_a_set_bh_utility_b'):
		core.skillModService.addSkillMod(actor, 'bh_dire_root', 1)
		core.skillModService.addSkillMod(actor, 'bh_dire_snare', 1)
		core.skillModService.addSkillMod(actor, 'fast_attack_line_dm_cc', 2)
		return
		
	##Enforcer
	elif object.getStfName() == ('item_ring_a_set_bh_dps'):
		core.skillModService.addSkillMod(actor, 'expertise_action_line_dm', 1)
		core.skillModService.addSkillMod(actor, 'expertise_action_line_dm_crit', 1)
		core.skillModService.addSkillMod(actor, 'expertise_buff_duration_line_bh_return_fire' , 1)
		core.skillModService.addSkillMod(actor, 'expertise_cooldown_line_dm' , 1)
		return
	return
	
def unequip(core, actor, object):

	##Direfate
	if object.getStfName() == ('item_ring_a_set_bh_utility_b'):
		core.skillModService.deductSkillMod(actor, 'bh_dire_root', 1)
		core.skillModService.deductSkillMod(actor, 'bh_dire_snare', 1)
		core.skillModService.deductSkillMod(actor, 'fast_attack_line_dm_cc', 2)
		return
		
	##Enforcer	
	elif object.getStfName() == ('item_ring_a_set_bh_dps'):
		core.skillModService.deductSkillMod(actor, 'expertise_action_line_dm', 1)
		core.skillModService.deductSkillMod(actor, 'expertise_action_line_dm_crit', 1)
		core.skillModService.deductSkillMod(actor, 'expertise_buff_duration_line_bh_return_fire' , 1)
		core.skillModService.deductSkillMod(actor, 'expertise_cooldown_line_dm' , 1)
		return
	return

	