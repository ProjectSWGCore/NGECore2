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
	return