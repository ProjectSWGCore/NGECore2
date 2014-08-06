import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('diseased_vrelt')
	mobileTemplates.add('noxious_vrelt_scavenger')
	
	core.spawnService.addLairTemplate('corellia_diseased_vrelt_lair_3', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_garbage_small_fog_mustard.iff')
	return