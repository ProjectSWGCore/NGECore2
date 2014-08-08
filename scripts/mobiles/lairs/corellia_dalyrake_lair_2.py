import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('dalyrake_martriarch')
	mobileTemplates.add('dalyrake_harvester')
	
	core.spawnService.addLairTemplate('corellia_dalyrake_lair_2', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_nest_large.iff')
	return