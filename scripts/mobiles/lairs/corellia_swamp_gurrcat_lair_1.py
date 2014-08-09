import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('gulginaw')
		
	core.spawnService.addLairTemplate('corellia_gulginaw_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_bones.iff')
	return