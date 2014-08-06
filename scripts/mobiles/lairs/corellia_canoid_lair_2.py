import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('canoid')
	mobileTemplates.add('canoid_hunter')
	mobileTemplates.add('canoid_pack_leader')
	
	core.spawnService.addLairTemplate('corellia_canoid_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_brambles_small.iff')
	return