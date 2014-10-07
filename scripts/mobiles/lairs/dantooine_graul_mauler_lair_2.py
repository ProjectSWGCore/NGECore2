import sys
from java.util import Vector

def addTemplate(core):
	
	mobileTemplates = Vector()
	mobileTemplates.add('graul_mauler')
	mobileTemplates.add('graul_mangler')
	
	core.spawnService.addLairTemplate('dantooine_graul_mauler_lair_1', mobileTemplates , 15, 'object/tangible/lair/base/poi_all_lair_rocks_large_evil_fire_red.iff')
	return