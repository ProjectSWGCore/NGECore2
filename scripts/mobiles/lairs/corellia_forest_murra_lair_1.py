import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('forest_murra')
	
	core.spawnService.addLairTemplate('corellia_forest_murra_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_dead_log_small_evil_fire_green.iff')
	return