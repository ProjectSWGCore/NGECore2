import sys

def setup(core, actor, buff):
	if actor.getSkillMod('expertise_buff_duration_line_me_enhance'):
		buff.setDuration(buff.getDuration()+(actor.getSkillModBase('expertise_buff_duration_line_me_enhance')))
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'strength_modified', 45)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'strength_modified', 45)
	return
	