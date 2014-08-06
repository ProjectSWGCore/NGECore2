import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('carrion_spat')
	mobileTemplates.add('carrion_spat_corpsereaver')
	
	core.spawnService.addLairTemplate('corellia_carrion_spat_lair_2', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_nest_large_evil_fire_small.iff')
	return