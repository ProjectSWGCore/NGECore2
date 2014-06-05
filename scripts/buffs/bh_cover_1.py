import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'expertise_damage_all', 10)
	core.skillModService.addSkillMod(actor, 'expertise_glancing_blow_ranged', 40)
	actor.setRadarVisible(False)
	return
	
def remove(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_damage_all', 10)
	core.skillModService.deductSkillMod(actor, 'expertise_glancing_blow_ranged', 40)
	actor.setRadarVisible(True)
	return
	