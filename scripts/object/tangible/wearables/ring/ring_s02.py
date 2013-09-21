import sys

def setup(core, object):
	return
	
def equip(core, actor, object):

	
	##Heroism
	if object.getStfName() == ('item_ring_a_set_hero'):
		core.skillModService.addSkillMod(actor, 'strength_modified', 30)
		core.skillModService.addSkillMod(actor, 'precision_modified', 30)
		core.skillModService.addSkillMod(actor, 'luck_modified', 30)
		return
	return
	
def unequip(core, actor, object):

	##Heroism
	if object.getStfName() == ('item_ring_a_set_hero'):
		core.skillModService.deductSkillMod(actor, 'strength_modified', 30)
		core.skillModService.deductSkillMod(actor, 'precision_modified', 30)
		core.skillModService.deductSkillMod(actor, 'luck_modified', 30)
		return
	return
		