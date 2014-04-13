import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/shared_rancor.iff')
	mobileTemplate.setCreatureName('rancor')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(75)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('defaultCreatureAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('rancor', mobileTemplate)