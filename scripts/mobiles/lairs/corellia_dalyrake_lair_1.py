import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('dalyrake')
	
	core.spawnService.addLairTemplate('corellia_dalyrake_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_mound_large.iff')
	return