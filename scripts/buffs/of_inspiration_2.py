import sys

def setup(core, actor, buff):
	actor.playEffectObject('appearance/pt_medic_healing_energy_area.prt', 'of_inspiration_2')
	actor.playEffectObject('clienteffect/off_inspiration.cef', 'of_inspiration_2')
	core.skillModService.addSkillMod(actor, 'expertise_action_all', 5)
	actor.setAction(actor.getAction() + 1111)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_action_all', 5)
	return