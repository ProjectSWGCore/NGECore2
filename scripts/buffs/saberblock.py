import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'display_only_parry', 1000 + (actor.getSkillModBase('expertise_saber_block') * 100))
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'display_only_parry', 1000 + (actor.getSkillModBase('expertise_saber_block') * 100))
	return
	