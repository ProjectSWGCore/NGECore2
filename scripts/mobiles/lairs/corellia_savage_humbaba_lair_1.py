import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('savage_humbaba')
		
	core.spawnService.addLairTemplate('corellia_savage_humbaba_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_brambles_large_evil_fire_red.iff')
	return