import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('bageraset')
	mobileTemplates.add('bageraset_bruiser')
		
	core.spawnService.addLairTemplate('corellia_bageraset_lair_2', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_leaf_small.iff')
	return