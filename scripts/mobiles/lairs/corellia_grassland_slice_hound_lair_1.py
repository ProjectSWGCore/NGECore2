import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('grassland_slice_hound')
	mobileTemplates.add('female_grassland_slice_hound')
	
	core.spawnService.addLairTemplate('corellia_grassland_slice_hound_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_bones_large_evil_fire_small.iff')
	return