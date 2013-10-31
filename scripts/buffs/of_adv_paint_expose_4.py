import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'stealth_divisor', 30)
	core.skillModService.addSkillMod(actor, 'expertise_damage_line_vulnerability_of_buff_def', 6)
	core.skillModService.addSkillMod(actor, 'expertise_damage_line_vulnerability_of_dm', 6)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'stealth_divisor', 30)
	core.skillModService.deductSkillMod(actor, 'expertise_damage_line_vulnerability_of_buff_def', 6)
	core.skillModService.deductSkillMod(actor, 'expertise_damage_line_vulnerability_of_dm', 6)
	return
	