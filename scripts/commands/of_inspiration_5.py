import sys

def setup(core, actor, buff):
	return
	
def run(core, actor, target, commandString):
	core.buffService.addBuffToCreature(actor, 'of_inspiration_5')
	actor.playEffectObject('clienteffect/off_inspiration.cef', 'root')
	return
	