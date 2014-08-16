import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('lesser_plains_bol')
	
	core.spawnService.addLairTemplate('dantooine_bol_lair_6', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_rocks_small.iff')
	return