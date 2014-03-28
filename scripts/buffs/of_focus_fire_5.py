import sys

def setup(core, actor, buff):
	if actor.getSkillMod('expertise_aura_maintain'):
		if actor.getSkillModBase('expertise_aura_maintain') > 1:
			buff.setDuration(-1)
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'combat_strikethrough_value', 4)
	core.skillModService.addSkillMod(actor, 'expertise_critical_niche_all', 29)
	core.skillModService.addSkillMod(actor, 'expertise_strikethrough_chance', 4)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'combat_strikethrough_value', 4)
	core.skillModService.deductSkillMod(actor, 'expertise_critical_niche_all', 29)
	core.skillModService.deductSkillMod(actor, 'expertise_strikethrough_chance', 4)
	return