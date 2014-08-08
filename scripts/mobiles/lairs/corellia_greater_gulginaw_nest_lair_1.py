import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('greater_gulginaw')
	
	core.spawnService.addLairTemplate('corellia_greater_gulginaw_nest_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_bones_evil_fire_small.iff')
	return