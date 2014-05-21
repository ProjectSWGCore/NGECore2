import sys

def setup(core, actor, buff):
		return

def add(core, actor, buff):
		actor.setInStealth(True)
		actor.setRadarVisible(False)

	if actor.getSkillMod('expertise_sp_covert_mastery_1'):
		core.skillModService.addSkillMod(actor, 'sp_covert_mastery')

		return

def remove(core, actor, buff):
		actor.setInStealth(False)
		actor.setRadarVisible(True)

	if actor.getSkillMod('expertise_sp_covert_mastery_1'):
		core.skillModService.deductSkillMod(actor, 'sp_covert_mastery')

		return
