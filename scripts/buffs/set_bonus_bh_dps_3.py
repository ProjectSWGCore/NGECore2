import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_action_line_dm', 10)
	core.skillModService.addSkillMod(actor, 'expertise_action_line_dm_crit', 10)
	core.skillModService.addSkillMod(actor, 'expertise_buff_duration_line_bh_return_fire' , 10)
	core.skillModService.addSkillMod(actor, 'expertise_cooldown_line_dm' , 10)
	core.skillModService.addSkillMod(actor, 'expertise_cooldown_line_bh_return_fire' , 750)	
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_action_line_dm', 10)
	core.skillModService.deductSkillMod(actor, 'expertise_action_line_dm_crit', 10)
	core.skillModService.deductSkillMod(actor, 'expertise_buff_duration_line_bh_return_fire' , 10)
	core.skillModService.deductSkillMod(actor, 'expertise_cooldown_line_dm' , 10)
	core.skillModService.deductSkillMod(actor, 'expertise_cooldown_line_bh_return_fire' , 750)
	return