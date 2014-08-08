import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('devil_gulginaw')
	
	core.spawnService.addLairTemplate('corellia_devil_gulginaw_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_bones_evil_fire_redpoi_all_lair_bones_evil_fire_red.iff')
	return