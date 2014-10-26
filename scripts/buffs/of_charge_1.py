import sys

def setup(core, actor, buff):
	if actor.getSkillMod('expertise_buff_duration_line_of_group_buff'):
		buff.setDuration(buff.getDuration() + actor.getSkillModBase('expertise_buff_duration_line_of_group_buff'))
	return

def add(core, actor, buff):
	return
	
def remove(core, actor, buff):
	return
	