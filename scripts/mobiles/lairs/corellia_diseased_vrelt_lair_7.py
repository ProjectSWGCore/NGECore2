import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('diseased_vrelt')
	mobileTemplates.add('diseased_vrelt_matriarch')
	
	core.spawnService.addLairTemplate('corellia_diseased_vrelt_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_garbage_small_fog_green.iff')
	return