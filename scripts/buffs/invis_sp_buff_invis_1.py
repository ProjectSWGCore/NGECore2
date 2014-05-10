import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	actor.playEffectObject('clienteffect/npe_smoke_bomb.cef', '')
	actor.setInStealth(True)
	actor.setRadarVisible(False)
	
	core.combatService.endCombat(actor)
	
	if actor.getSkillMod('expertise_sp_without_a_trace_1'):
		core.buffService.addBuffToCreature(actor, 'invis_sp_buff_invis_notrace_1')
	
	return
	
def remove(core, actor, buff):
	actor.setInStealth(False)
	actor.setRadarVisible(True)
	
	if actor.getSkillMod('expertise_sp_without_a_trace_1'):
		core.buffService.removeBuffFromCreature(actor, 'invis_sp_buff_invis_notrace_1')
	
	return
	
