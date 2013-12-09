import sys

	#Placeholder for now needs to be re-worked
def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'combat_divide_damage_taken', 90)
	core.skillModService.addSkillMod(actor, 'combat_divide_damage_dealt', 90)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'combat_divide_damage_taken', 90)
	core.skillModService.deductSkillMod(actor, 'combat_divide_damage_dealt', 90)
	return