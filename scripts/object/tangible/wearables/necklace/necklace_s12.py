import sys

def setup(core, object):
	return
	
def equip(core, actor, object):

	##Juggernaut
	if object.getStfName() == ('item_necklace_set_commando_utility_b_01_01'):
		core.skillModService.addSkillMod(actor, 'constitution_modified', 10)
		core.skillModService.addSkillMod(actor, 'strength_modified' , 15)
		core.skillModService.addSkillMod(actor, 'expertise_devastation_bonus' , 5)
		
		Buff = actor.getBuffByName('set_bonus_commando_utility_b_3')
		if actor.getBuffList().contains(Buff):
			core.buffService.removeBuffFromCreature(actor, Buff)
			return
		
		if actor:
			core.buffService.addBuffToCreature(actor, 'set_bonus_commando_utility_b_3')
			return
		return
	return
	
def unequip(core, actor, object):

	##Juggernaut
	if object.getStfName() == ('item_necklace_set_commando_utility_b_01_01'):
		core.skillModService.deductSkillMod(actor, 'constitution_modified', 10)
		core.skillModService.deductSkillMod(actor, 'strength_modified' , 15)
		core.skillModService.deductSkillMod(actor, 'expertise_devastation_bonus' , 5)
		
		Buff = actor.getBuffByName('set_bonus_commando_utility_b_3')
		if actor.getBuffList().contains(Buff):
			core.buffService.removeBuffFromCreature(actor, Buff)
			return
		return
	return
	