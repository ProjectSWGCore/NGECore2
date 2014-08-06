import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('diseased_vrelt')
	
	core.spawnService.addLairTemplate('corellia_diseased_vrelt_lair_2', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_garbage_small_evil_fire_green.iff')
	return