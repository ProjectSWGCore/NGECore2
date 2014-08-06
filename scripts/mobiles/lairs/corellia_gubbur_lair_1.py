import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('gubbur')
		
	core.spawnService.addLairTemplate('corellia_gubbur_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_leaf_small.iff')
	return