import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/shared_kashyyyk_bantha.iff')
	mobileTemplate.setCreatureName('kashyyyk_bantha')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(80)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('defaultCreatureAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('kashyyyk_bantha', mobileTemplate)