import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('forest_slice_hound')
	
	core.spawnService.addLairTemplate('corellia_forest_slice_hound_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_bones_evil_fire_green.iff')
	return