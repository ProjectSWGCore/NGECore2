import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('grand_wrix')

	core.spawnService.addLairTemplate('corellia_grand_wrix_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_rocks_large_evil_fire_green.iff')
	return