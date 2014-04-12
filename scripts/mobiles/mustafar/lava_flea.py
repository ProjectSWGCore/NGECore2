import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/som/shared_lava_flea.iff')
	mobileTemplate.setCreatureName('lava_flea')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(50)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShot')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('lava_flea', mobileTemplate)