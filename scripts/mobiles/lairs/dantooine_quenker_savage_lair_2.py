import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('savage_quenker')
	mobileTemplates.add('terrible_quenker')
	
	core.spawnService.addLairTemplate('dantooine_quenker_savage_lair_2', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_warren_small_evil_fire_red.iff')
	return