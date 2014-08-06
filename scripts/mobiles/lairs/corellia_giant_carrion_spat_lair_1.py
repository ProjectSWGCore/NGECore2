import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('giant_carrion_spat')
	
	core.spawnService.addLairTemplate('corellia_giant_carrion_spat_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_nest_large_evil_fire_red.iff')
	return