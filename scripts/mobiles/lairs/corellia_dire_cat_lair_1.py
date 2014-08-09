import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('dire_cat')
	
	core.spawnService.addLairTemplate('corellia_dire_cat_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_rocks_small.iff')
	return