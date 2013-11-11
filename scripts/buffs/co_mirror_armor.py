import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'radar_invis', 0)
	
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'radar_invis', 0)
	return
	