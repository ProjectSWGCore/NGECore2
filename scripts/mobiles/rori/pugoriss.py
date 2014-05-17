import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/shared_pugoriss.iff')
	mobileTemplate.setCreatureName('pugoriss')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(42)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('pugoriss', mobileTemplate)
	