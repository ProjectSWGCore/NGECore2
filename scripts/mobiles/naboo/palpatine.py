from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/shared_palpatine.iff')
	mobileTemplate.setCreatureName('palpatine')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(60)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('defaultCreatureAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('palpatine', mobileTemplate)