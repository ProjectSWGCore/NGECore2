import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('razor_cat')
	mobileTemplates.add('female_razor_cat')
	mobileTemplates.add('razor_cat_eviscerator')
	
	core.spawnService.addLairTemplate('corellia_razor_cat_pride_lair_2', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_rock_shelter_large_evil_fire_red.iff')
	return