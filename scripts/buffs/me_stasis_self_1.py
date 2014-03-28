import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	actor.playEffectObject('clienteffect/medic_stasis.cef', 'me_stasis_self_1')
	actor.setSpeedMultiplierBase(0.2)
	return
	
def remove(core, actor, buff):
	actor.setSpeedMultiplierBase(1)
	return
	