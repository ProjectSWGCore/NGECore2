import sys

def setup(core, object):
	return
	
def equip(core, actor, object):

	##Frontman
	if object.getStfName() == ('item_necklace_set_commando_utility_a_01_01'):
		core.skillModService.addSkillMod(actor, 'constitution_modified', 20)
		core.skillModService.addSkillMod(actor, 'display_only_block' , 40)
		core.skillModService.addSkillMod(actor, 'combat_block_value' , 50)
		
		Buff = actor.getBuffByName('set_bonus_commando_utility_a_3')
		if actor.getBuffList().contains(Buff):
			core.buffService.removeBuffFromCreature(actor, Buff)
			return
		
		if actor:
			core.buffService.addBuffToCreature(actor, 'set_bonus_commando_utility_a_3')
			return
		return
	return
	
def unequip(core, actor, object):

	##Frontman
	if object.getStfName() == ('item_necklace_set_commando_utility_a_01_01'):
		core.skillModService.deductSkillMod(actor, 'constitution_modified', 20)
		core.skillModService.deductSkillMod(actor, 'display_only_block' , 40)
		core.skillModService.deductSkillMod(actor, 'combat_block_value' , 50)
		
		Buff = actor.getBuffByName('set_bonus_commando_utility_a_3')
		if actor.getBuffList().contains(Buff):
			core.buffService.removeBuffFromCreature(actor, Buff)
			return
		return
	return
	