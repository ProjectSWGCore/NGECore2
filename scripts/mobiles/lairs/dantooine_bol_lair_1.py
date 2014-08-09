import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('bol')
	mobileTemplates.add('bol_pack_runner')
	mobileTemplates.add('baby_bol')
	
	core.spawnService.addLairTemplate('dantooine_bol_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_rock_shelter_large.iff')
	return