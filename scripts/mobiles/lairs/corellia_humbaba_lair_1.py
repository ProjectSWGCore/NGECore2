import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('humbaba')
		
	core.spawnService.addLairTemplate('corellia_humbaba_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_brambles_large.iff')
	return