from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/shared_r2d2.iff')
	mobileTemplate.setCreatureName('r2d2')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(59)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('defaultCreatureAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('r2d2', mobileTemplate)