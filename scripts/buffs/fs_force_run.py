import sys

def setup(core, actor, buff):
	actor.playEffectObject('clienteffect/pl_force_run.cef', 'fs_force_run')
	actor.playEffectObject('appearance/pt_force_speed.prt', 'fs_force_run')
	core.skillModService.addSkillMod(actor, 'slope_move', 5)
	core.skillModService.addSkillMod(actor, 'movement', 0)
	core.skillModService.addSkillMod(actor, 'movement_resist_snare', 100)
	core.skillModService.addSkillMod(actor, 'movement_resist_root', 100)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'slope_move', 5)
	core.skillModService.deductSkillMod(actor, 'movement', 0)
	core.skillModService.deductSkillMod(actor, 'movement_resist_snare', 100)

	return