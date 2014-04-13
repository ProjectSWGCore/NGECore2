from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/shared_jabba_the_hutt.iff')
	mobileTemplate.setCreatureName('jabba_the_hutt')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(59)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('defaultCreatureAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('jabba_the_hutt', mobileTemplate)