import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('wooded_paralope')
		
	core.spawnService.addLairTemplate('corellia_wooded_paralope_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_thicket_small_evil_fire_green.iff')
	return