import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('savage_quenker')
	
	core.spawnService.addLairTemplate('dantooine_quenker_savage_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_warren_small_fog_green.iff')
	return