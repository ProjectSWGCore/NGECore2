import sys

def setup(core, actor, buff):
	return
	
def run(core, actor, target, commandString):
	core.buffService.addBuffToCreature(actor, 'of_emergency_shield')
	actor.playEffectObject('clienteffect/off_em_shields.cef', 'root')
	return
	