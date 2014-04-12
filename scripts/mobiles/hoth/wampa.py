import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/shared_wampa.iff')
	mobileTemplate.setCreatureName('wampa')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(50)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShot')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('wampa', mobileTemplate)