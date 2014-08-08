import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('durni')
	mobileTemplates.add('vehement_warrior')
	
	core.spawnService.addLairTemplate('corellia_durni_warren_lair_2', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_warren_small_evil_fire_small.iff')
	return