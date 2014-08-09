import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('gurrcat')
		
	core.spawnService.addLairTemplate('corellia_gurrcat_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_bones.iff')
	return