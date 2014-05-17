import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/shared_gualama.iff')
	mobileTemplate.setCreatureName('gualama')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(39)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('gualama', mobileTemplate)
	