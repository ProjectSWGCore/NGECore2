import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	attacks = Vector()
	mobileTemplates.setAttacks(attacks)
	templates = Vector()
	templates.add('object/mobile/shared_fambaa.iff')
	mobileTemplate.setCreatureName('fambaa')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(23)
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('fambaa', mobileTemplate)
	