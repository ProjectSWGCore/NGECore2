import sys

def setup(core, object):
	return
	
def equip(core, actor, target):
	core.skillModService.addSkillMod(actor, 'agility_modified', 21)
	
	return
	
def unequip(core, actor, target):
	core.skillModService.deductSkillMod(actor, 'agility_modified', 21)

	return
	