import sys

def setup(core, actor, buff):
	if actor.getSkillMod('expertise_buff_duration_line_me_enhance'):
		buff.setDuration(buff.getDuration()+(actor.getSkillModBase('expertise_buff_duration_line_me_enhance')))
	core.skillModService.addSkillMod(actor, 'display_only_block', 1000)
	core.skillModService.addSkillMod(actor, 'combat_block_value', 200)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'display_only_block', 1000)
	core.skillModService.deductSkillMod(actor, 'combat_block_value', 200)	
	return
	