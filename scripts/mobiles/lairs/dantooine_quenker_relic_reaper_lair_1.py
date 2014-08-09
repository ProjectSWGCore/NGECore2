import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('quenker_relic_reaper')
	
	core.spawnService.addLairTemplate('dantooine_quenker_relic_reaper_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/poi_all_lair_warren_small_fog_green.iff')
	return