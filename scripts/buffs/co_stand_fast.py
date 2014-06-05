import sys

def setup(core, actor, buff):
	if actor.getSkillMod('expertise_buff_duration_line_co_stand_fast'):
		buff.setDuration(buff.getDuration() + actor.getSkillMod('expertise_buff_duration_line_co_stand_fast').getBase())
	return

def add(core, actor, buff):
	if actor.getSkillMod('expertise_damage_decrease_percentage'):
		core.skillModService.addSkillMod(actor, 'damage_decrease_percentage', (60 + actor.getSkillMod('expertise_damage_decrease_percentage').getBase()) / 2)
	else:
		core.skillModService.addSkillMod(actor, 'damage_decrease_percentage', 60 / 2)
	
	return
	
def remove(core, actor, buff):
	if actor.getSkillMod('expertise_damage_decrease_percentage'):
		core.skillModService.deductSkillMod(actor, 'damage_decrease_percentage', (60 + actor.getSkillMod('expertise_damage_decrease_percentage').getBase()) / 2)
	else:
		core.skillModService.deductSkillMod(actor, 'damage_decrease_percentage', 60 / 2)
	return