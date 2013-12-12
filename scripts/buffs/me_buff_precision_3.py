import sys

def setup(core, actor, buff):
	if actor.getSkillMod('expertise_buff_duration_line_me_enhance'):
		buff.setDuration(buff.getDuration()+(actor.getSkillModBase('expertise_buff_duration_line_me_enhance')))
	core.skillModService.addSkillMod(actor, 'precision_modified', 80)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'precision_modified', 80)
	return
	