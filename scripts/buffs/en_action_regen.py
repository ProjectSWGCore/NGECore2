import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_action_weapon_melee', 60)
	
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_action_weapon_melee', 60)
	return