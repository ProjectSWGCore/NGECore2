import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	mobileTemplates.setAttacks(attacks)
	templates = Vector()
	templates.add('object/mobile/shared_hermit_spider.iff')
	mobileTemplate.setCreatureName('hermit spider')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(28)
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('hermit_spider', mobileTemplate)
	