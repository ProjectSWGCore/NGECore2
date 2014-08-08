import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('majestic_plumed_rasp')
		
	core.spawnService.addLairTemplate('corellia_plumed_rasp_eyrie_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_nest_small_evil_fire_small.iff')
	return