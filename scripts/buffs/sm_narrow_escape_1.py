import sys

def setup(core, actor, buff):
	actor.playEffectObject('clienteffect/sm_narrow_escape.cef', 'sm_narrow_escape_1')
	core.skillModService.addSkillMod(actor, 'slope_move', 2)
	core.skillModService.addSkillMod(actor, 'movement_resist_snare', 100)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'slope_move', 2)
	core.skillModService.deductSkillMod(actor, 'movement_resist_snare', 100)

	return