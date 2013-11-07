import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_damage_all', 5)
	core.skillModService.addSkillMod(actor, 'expertise_action_all', 5)
	core.skillModService.addSkillMod(actor, 'expertise_damage_weapon_2', 5)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_damage_all', 5)
	core.skillModService.deductSkillMod(actor, 'expertise_action_all', 5)
	core.skillModService.deductSkillMod(actor, 'expertise_damage_weapon_2', 5)
	return
	