import sys
from java.util import Vector

def addTemplate(core):
	mobileTemplates = Vector()
	mobileTemplates.add('huurton_matron')

	
	core.spawnService.addLairTemplate('dantooine_huurton_matron_lair_1', mobileTemplates, 15, 'object/tangible/lair/base/bramble_light.iff')
	return