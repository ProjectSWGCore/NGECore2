import sys

def setup(core, actor, buff):
	if actor.getSkillMod('expertise_buff_duration_line_me_enhance'):
		buff.setDuration(buff.getDuration()+(actor.getSkillModBase('expertise_buff_duration_line_me_enhance')))
	core.skillModService.addSkillMod(actor, 'agility_modified', 45)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'agility_modified', 45)
	return
	