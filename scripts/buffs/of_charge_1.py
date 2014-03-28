import sys

def setup(core, actor, buff):
	if actor.getSkillMod('expertise_buff_duration_line_of_group_buff'):
		buff.setDuration(buff.getDuration() + actor.getSkillModBase('expertise_buff_duration_line_of_group_buff'))
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_damage_all', 10)
	core.skillModService.addSkillMod(actor, 'expertise_action_all', 10)
	core.skillModService.addSkillMod(actor, 'movement_resist_snare', 100)
	core.skillModService.addSkillMod(actor, 'movement', 5)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'movement', 5)
	core.skillModService.deductSkillMod(actor, 'expertise_damage_all', 10)
	core.skillModService.deductSkillMod(actor, 'expertise_action_all', 10)
	core.skillModService.deductSkillMod(actor, 'movement_resist_snare', 100)
	return
	