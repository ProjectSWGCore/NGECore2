import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('scavenger_rat')
		
	core.spawnService.addLairTemplate('corellia_scavenger_rat_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_garbage_small.iff')
	return