from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/shared_acklay_hue.iff')
	mobileTemplate.setCreatureName('acklay')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(60)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('defaultCreatureAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('acklay', mobileTemplate)