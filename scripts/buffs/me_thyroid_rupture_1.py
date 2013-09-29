import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'combat_divide_damage_dealt', 25)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'combat_divide_damage_dealt', 25)
	return	