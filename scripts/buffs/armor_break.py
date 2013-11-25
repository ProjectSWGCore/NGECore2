import sys

def setup(core, actor, buff):
	actor.addSkillMod('expertise_innate_reduction_all_player', 6 * buff.getStacks())
	return
	
def removeBuff(core, actor, buff):
	actor.deductSkillMod('expertise_innate_reduction_all_player', 6 * buff.getStacks())
	return
	