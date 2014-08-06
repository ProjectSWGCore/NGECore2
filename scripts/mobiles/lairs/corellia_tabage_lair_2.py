import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('tabage_scavenger')
		
	core.spawnService.addLairTemplate('corellia_tabage_lair_2', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_brambles_small_evil_fire_small.iff')
	return