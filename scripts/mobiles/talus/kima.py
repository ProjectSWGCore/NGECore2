import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/shared_kima.iff')
	mobileTemplate.setCreatureName('kima')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(45)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('kima', mobileTemplate)
	