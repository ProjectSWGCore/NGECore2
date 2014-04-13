from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/shared_droideka.iff')
	mobileTemplate.setCreatureName('droideka')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(59)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('defaultCreatureAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('droideka', mobileTemplate)