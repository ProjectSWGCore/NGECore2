import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('noxious_vrelt_scavenger')
	
	core.spawnService.addLairTemplate('corellia_diseased_vrelt_lair_4', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_garbage_small_fog_green.iff')
	return