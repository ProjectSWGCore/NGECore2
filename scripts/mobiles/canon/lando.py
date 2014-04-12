from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/shared_lando.iff')
	mobileTemplate.setCreatureName('lando')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(99)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('defaultCreatureAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('lando', mobileTemplate)