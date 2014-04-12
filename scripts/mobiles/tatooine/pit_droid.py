from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/shared_pit_droid.iff')
	mobileTemplate.setCreatureName('pit_droid')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(10)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('defaultCreatureAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('pit_droid', mobileTemplate)