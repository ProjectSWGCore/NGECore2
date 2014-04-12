import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/shared_atxt.iff')
	mobileTemplate.setCreatureName('atxt')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(99)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShot')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('atxt', mobileTemplate)
	