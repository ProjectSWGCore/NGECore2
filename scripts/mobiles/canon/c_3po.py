from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/shared_c_3po.iff')
	mobileTemplate.setCreatureName('c_3po')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(59)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('defaultCreatureAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('c_3po', mobileTemplate)