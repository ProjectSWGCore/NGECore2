from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/shared_emperor_royal_guard.iff')
	mobileTemplate.setCreatureName('emperor_royal_guard')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(60)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('defaultCreatureAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('emperor_royal_guard', mobileTemplate)