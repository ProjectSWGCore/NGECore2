import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('huurton_pub')
	mobileTemplates.add('huurton_reaper')
	mobileTemplates.add('huurton_stalker')
	
	core.spawnService.addLairTemplate('dantooine_huurton_lair_2', mobileTemplates, 15, 'object/tangible/lair/base/shared_poi_all_lair_brambles_small.iff')
	return