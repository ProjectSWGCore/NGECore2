import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('quenker_ravager')
	
	core.spawnService.addLairTemplate('dantooine_quenker_ravager_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_warren_small_evil_fire_green.iff')
	return