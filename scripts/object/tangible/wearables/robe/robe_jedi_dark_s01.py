import sys

def setup(core, object):
	return
	
def equip(core, actor, target):
	core.skillModService.addSkillMod(actor, 'constitution_modified', 100)
	core.skillModService.addSkillMod(actor, 'precision_modified', 100)
	
	return
	
def unequip(core, actor, target):
	core.skillModService.deductSkillMod(actor, 'constitution_modified', 100)
	core.skillModService.deductSkillMod(actor, 'precision_modified', 100)


	return
	