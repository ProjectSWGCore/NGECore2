import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('sharnaff')
	mobileTemplates.add('sharnaff_reckless_ravager')
	mobileTemplates.add('sharnaff_bull')	
	
	core.spawnService.addLairTemplate('corellia_sharnaff_lair_2', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_rocks_large_evil_fire_small.iff')
	return