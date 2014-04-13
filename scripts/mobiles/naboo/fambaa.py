from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/shared_fambaa.iff')
	mobileTemplate.setCreatureName('fambaa')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(25)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('defaultCreatureAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('fambaa', mobileTemplate)