import sys

def setup(core, actor, buff):
	
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_damage_npc', 200)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_damage_npc', -200)
	return
	