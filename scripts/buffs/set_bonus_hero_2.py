import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'stamina_modified', 90)
	core.skillModService.addSkillMod(actor, 'agility_modified', 90)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'stamina_modified', 90)
	core.skillModService.deductSkillMod(actor, 'agility_modified', 90)
	return