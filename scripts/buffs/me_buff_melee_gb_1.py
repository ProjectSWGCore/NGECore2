import sys

def setup(core, actor, buff):
	if actor.getSkillMod('expertise_buff_duration_line_me_enhance'):
		buff.setDuration(buff.getDuration()+(actor.getSkillModBase('expertise_buff_duration_line_me_enhance')))
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'display_only_block', 1000)
	core.skillModService.addSkillMod(actor, 'combat_block_value', 200)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'display_only_block', 1000)
	core.skillModService.deductSkillMod(actor, 'combat_block_value', 200)	
	return
	