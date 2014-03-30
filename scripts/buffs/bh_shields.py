import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):
	actor.playEffectObject('clienteffect/bh_shields_effect.cef', 'bh_shields')
	return
	
def remove(core, actor, buff):
	actor.stopEffectObject('bh_shields')
	return
	