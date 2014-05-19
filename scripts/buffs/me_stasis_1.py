import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	actor.playEffectObject('clienteffect/medic_stasis.cef', 'me_stasis_1')
	core.skillModService.deductSkillMod(actor, 'movement', 8)
	return
	
def remove(core, actor, buff):
	core.skillModService.addSkillMod(actor, 'movement', 8)
	return
	