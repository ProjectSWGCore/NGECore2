import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	actor.addSkillMod('expertise_innate_reduction_all_player', 6 * buff.getStacks())
	actor.addSkillMod('expertise_innate_reduction_all_mob', 600 * buff.getStacks())
	return
	
def remove(core, actor, buff):
	actor.deductSkillMod('expertise_innate_reduction_all_mob', 600 * buff.getStacks())
	actor.deductSkillMod('expertise_innate_reduction_all_player', 6 * buff.getStacks())
	return
