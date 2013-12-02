import sys

def setup(core, actor, buff):
	actor.playEffectObject('appearance/pt_medic_healing_energy_area.prt', 'of_inspiration_6')
	actor.playEffectObject('clienteffect/off_inspiration.cef', 'of_inspiration_6')
	core.skillModService.addSkillMod(actor, 'expertise_action_all', 5)
	actor.setAction(actor.getAction() + 2800)
	return
	
def removeBuff(core, actor, buff):
	core.skillModService.deductSkillMod(actor, 'expertise_action_all', 5)
	return