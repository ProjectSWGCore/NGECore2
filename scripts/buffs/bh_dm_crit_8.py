import sys

def setup(core, actor, buff):
	actor.addSkillMod('expertise_innate_reduction_all_player', 6 * buff.getStacks())
	actor.addSkillMod('expertise_innate_reduction_all_mob', 600 * buff.getStacks())
	core.skillModService.addSkillMod(actor, 'critical_damage_vulnerability', 8)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'critical_damage_vulnerability', 8)
	actor.deductSkillMod('expertise_innate_reduction_all_mob', 600 * buff.getStacks())
	actor.deductSkillMod('expertise_innate_reduction_all_player', 6 * buff.getStacks())
	return
	