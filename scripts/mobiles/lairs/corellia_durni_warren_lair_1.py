import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('durni')
	
	core.spawnService.addLairTemplate('corellia_durni_warren_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_warren_small.iff')
	return