import sys

def setup(core, actor, buff):
	buff.setDuration(buff.getDuration()+(actor.getSkillModBase('expertise_buff_duration_line_bh_return_fire')))
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_bh_return_fire_1', 1)
	
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_bh_return_fire_1', 1)
	return
	