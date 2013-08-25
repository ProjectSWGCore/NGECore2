import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'stamina_modified', 35)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'stamina_modified', 35)
	return
	