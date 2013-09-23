import sys

def setup(core, object):
	return
	
def equip(core, actor, object):

	##Direfate
	if object.getStfName() == ('item_necklace_set_bh_utility_b_01_01'):
		core.skillModService.addSkillMod(actor, 'bh_dire_root', 1)
		core.skillModService.addSkillMod(actor, 'bh_dire_snare', 1)
		core.skillModService.addSkillMod(actor, 'fast_attack_line_dm_cc', 2)
		
		Buff = actor.getBuffByName('set_bonus_bh_utility_b_3')
		if actor.getBuffList().contains(Buff):
			core.buffService.removeBuffFromCreature(actor, Buff)
			return
		
		if actor:
			core.buffService.addBuffToCreature(actor, 'set_bonus_bh_utility_b_3')
			return
		return
	return
	
def unequip(core, actor, object):

	##Direfate
	if object.getStfName() == ('item_necklace_set_bh_utility_b_01_01'):
		core.skillModService.deductSkillMod(actor, 'bh_dire_root', 1)
		core.skillModService.deductSkillMod(actor, 'bh_dire_snare', 1)
		core.skillModService.deductSkillMod(actor, 'fast_attack_line_dm_cc', 2)
		
		Buff = actor.getBuffByName('set_bonus_bh_utility_b_3')
		if actor.getBuffList().contains(Buff):
			core.buffService.removeBuffFromCreature(actor, Buff)
			return
		return
	return

	