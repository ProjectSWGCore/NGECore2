import sys

def add(core, actor, name, base):
	actor.addSkillMod(name, base)
	core.skillModService.addSkillMod(actor, 'stamina_modified', (actor.getSkillModBase('expertise_focus_stamina')))
	core.skillModService.addSkillMod(actor, 'display_only_critical', 100 * (actor.getSkillModBase('expertise_focus_critical_buff_line')))
	core.skillModService.addSkillMod(actor, 'expertise_damage_melee', 1 * (actor.getSkillModBase('expertise_focus_damage_increase')))
	return
	
def deduct(core, actor, name, base):
	actor.deductSkillMod(name, base)
	core.skillModService.deductSkillMod(actor, 'stamina_modified', (actor.getSkillModBase('expertise_focus_stamina')))
	core.skillModService.deductSkillMod(actor, 'display_only_critical', 100 * (actor.getSkillModBase('expertise_focus_critical_buff_line')))
	core.skillModService.deductSkillMod(actor, 'expertise_damage_melee', 1 * (actor.getSkillModBase('expertise_focus_damage_increase')))
	return
	