import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('tabage')
		
	core.spawnService.addLairTemplate('corellia_tabage_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_brambles_small.iff')
	return