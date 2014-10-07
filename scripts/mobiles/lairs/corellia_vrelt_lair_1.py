import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('vrelt')
		
	core.spawnService.addLairTemplate('corellia_vrelt_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_garbage_small.iff')
	return