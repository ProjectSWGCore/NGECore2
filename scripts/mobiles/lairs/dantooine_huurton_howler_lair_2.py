import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('huurton_howler')
	mobileTemplates.add('huurton_bloodhunter')
	
	core.spawnService.addLairTemplate('dantooine_huurton_howler_lair_2', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_brambles_large_fog_mustard.iff')
	return