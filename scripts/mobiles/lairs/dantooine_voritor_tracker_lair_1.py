import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('grassland_voritor_tracker') 
	
	core.spawnService.addLairTemplate('dantooine_voritor_tracker_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_thicket_small_fog_green.iff')
	return