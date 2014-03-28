import sys

def setup(core, actor, buff):
	if actor.getSkillMod('expertise_aura_maintain'):
		if actor.getSkillModBase('expertise_aura_maintain') > 2:
			buff.setDuration(-1)
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_damage_all', 5)
	core.skillModService.addSkillMod(actor, 'expertise_action_all', 5)
	core.skillModService.addSkillMod(actor, 'expertise_damage_weapon_2', 5)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_damage_all', 5)
	core.skillModService.deductSkillMod(actor, 'expertise_action_all', 5)
	core.skillModService.deductSkillMod(actor, 'expertise_damage_weapon_2', 5)
	return
	