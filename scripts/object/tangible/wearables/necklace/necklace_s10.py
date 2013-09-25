import sys

def setup(core, object):
	return
	
def equip(core, actor, object):

	##Heroism
	if object.getStfName() == ('item_necklace_set_hero_01_01'):
		core.skillModService.addSkillMod(actor, 'strength_modified', 30)
		core.skillModService.addSkillMod(actor, 'precision_modified', 30)
		core.skillModService.addSkillMod(actor, 'luck_modified', 30)
		
		Buff = actor.getBuffByName('set_bonus_hero_3')
		if actor.getBuffList().contains(Buff):
			core.buffService.removeBuffFromCreature(actor, Buff)
			return
		
		if actor:
			core.buffService.addBuffToCreature(actor, 'set_bonus_hero_3')
			return
		return
		
	##Grenadier
	elif object.getStfName() == ('item_necklace_set_commando_dps_01_01'):
		core.skillModService.addSkillMod(actor, 'fast_attack_line_co_grenade', 3)
		core.skillModService.addSkillMod(actor, 'expertise_action_line_co_grenade' , 2)
		core.skillModService.addSkillMod(actor, 'expertise_freeshot_co_grenade' , 2)
		
		Buff = actor.getBuffByName('set_bonus_commando_dps_3')
		if actor.getBuffList().contains(Buff):
			core.buffService.removeBuffFromCreature(actor, Buff)
			return
		
		if actor:
			core.buffService.addBuffToCreature(actor, 'set_bonus_commando_dps_3')
			return
		return
	return
	
def unequip(core, actor, object):

	##Heroism
	if object.getStfName() == ('item_necklace_set_hero_01_01'):
		core.skillModService.deductSkillMod(actor, 'strength_modified', 30)
		core.skillModService.deductSkillMod(actor, 'precision_modified', 30)
		core.skillModService.deductSkillMod(actor, 'luck_modified', 30)
		
		Buff = actor.getBuffByName('set_bonus_hero_3')
		if actor.getBuffList().contains(Buff):
			core.buffService.removeBuffFromCreature(actor, Buff)
			return
		return
		
	##Grenadier
	elif object.getStfName() == ('item_necklace_set_commando_dps_01_01'):
		core.skillModService.deductSkillMod(actor, 'fast_attack_line_co_grenade', 3)
		core.skillModService.deductSkillMod(actor, 'expertise_action_line_co_grenade' , 2)
		core.skillModService.deductSkillMod(actor, 'expertise_freeshot_co_grenade' , 2)
		
		Buff = actor.getBuffByName('set_bonus_commando_dps_3')
		if actor.getBuffList().contains(Buff):
			core.buffService.removeBuffFromCreature(actor, Buff)
			return
		return
	return