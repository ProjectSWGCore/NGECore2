import sys

def setup(core, actor, buff):
	
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'agility_modified', 100)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'agility_modified', 100)
	return
	