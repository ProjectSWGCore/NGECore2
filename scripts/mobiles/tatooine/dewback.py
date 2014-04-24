import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/shared_dewback.iff')
	mobileTemplate.setCreatureName('dewback')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(17)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('dewback', mobileTemplate)
	