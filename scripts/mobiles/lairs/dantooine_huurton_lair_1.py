import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('huurton')
	mobileTemplates.add('huurton_howler')
	mobileTemplates.add('huurton_matron')
	
	core.spawnService.addLairTemplate('dantooine_huurton_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/shared_poi_all_lair_brambles_small.iff')
	return