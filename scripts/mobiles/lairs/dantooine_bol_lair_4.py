import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('bol')
	mobileTemplates.add('bol_pack_runner')
	mobileTemplates.add('swift_charging_bol')
	
	core.spawnService.addLairTemplate('dantooine_bol_lair_4', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_rock_shelter_large.iff')
	return