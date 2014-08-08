import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('giant_gubbur')
	
	core.spawnService.addLairTemplate('corellia_giant_gubbur_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_leaf_small_evil_fire_small.iff')
	return