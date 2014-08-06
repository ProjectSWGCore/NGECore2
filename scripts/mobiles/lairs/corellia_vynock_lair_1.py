import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('vynock')
		
	core.spawnService.addLairTemplate('corellia_vynock_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_nest_small.iff')
	return