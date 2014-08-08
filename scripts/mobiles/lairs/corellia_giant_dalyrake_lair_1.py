import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('giant_dalyrake')
	
	core.spawnService.addLairTemplate('corellia_giant_dalyrake_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_mound_large_evil_fire_green.iff')
	return