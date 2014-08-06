import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('thune')
	
	core.spawnService.addLairTemplate('dantooine_thune_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_rocks_large_fog_green.iff')
	return