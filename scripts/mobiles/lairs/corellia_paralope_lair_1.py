import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('paralope')
		
	core.spawnService.addLairTemplate('corellia_paralope_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_thicket_small.iff')
	return