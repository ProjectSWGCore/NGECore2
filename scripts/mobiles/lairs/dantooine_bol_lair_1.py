import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('lesser_plains_bol')
	mobileTemplates.add('seething_bol_crusher')
	mobileTemplates.add('swift_charging_bol')
	core.spawnService.addLairTemplate('dantooine_bol_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/shared_poi_all_lair_brambles_small.iff')