import sys

def setup(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'stealth_divisor', 40)
	core.skillModService.addSkillMod(actor, 'expertise_damage_line_vulnerability_of_buff_def', 8)
	core.skillModService.addSkillMod(actor, 'expertise_damage_line_vulnerability_of_dm', 8)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'stealth_divisor', 40)
	core.skillModService.deductSkillMod(actor, 'expertise_damage_line_vulnerability_of_buff_def', 8)
	core.skillModService.deductSkillMod(actor, 'expertise_damage_line_vulnerability_of_dm', 8)
	return
	