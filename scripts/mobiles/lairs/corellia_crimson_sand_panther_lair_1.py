import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('crimson_razor_cat')
	
	core.spawnService.addLairTemplate('corellia_crimson_sand_panther_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_rock_shelter_large_fog_red.iff')
	return