import sys
from java.lang import Float

def setup(core, actor, buff):
	
	#powerup = actor.getUseTarget()
	powerupID = actor.getAttachment('LastUsedPUP')
	powerup = core.objectService.getObject(long(powerupID))
	effectName = powerup.getAttachment("effectName");
	powerValue = powerup.getAttachment("powerValue");
	effectName = effectName.replace('@stat_n:', '')
	actor.sendSystemMessage('en %s' % effectName, 0)
	actor.sendSystemMessage('pv %s' % powerValue, 0)
	buff.setDuration(Float(1800.0))
	if effectName:
		buff.setEffect1Name(effectName)
	if powerValue:
		buff.setEffect1Value(Float(powerValue))
	return

def add(core, actor, buff):
	
	return

def remove(core, actor, buff):

	return