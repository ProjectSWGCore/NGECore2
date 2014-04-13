from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/ep3/shared_general_grievous.iff')
	mobileTemplate.setCreatureName('general_grievous')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(80)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('defaultCreatureAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('general_grievous', mobileTemplate)