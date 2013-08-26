import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'agility_modified', 45)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'agility_modified', 45)
	return
	