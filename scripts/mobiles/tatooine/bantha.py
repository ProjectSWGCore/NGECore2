import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/shared_bantha.iff')
	mobileTemplate.setCreatureName('bantha')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(10)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('defaultCreatureAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('bantha', mobileTemplate)
	