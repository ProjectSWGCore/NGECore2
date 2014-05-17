import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/shared_fynock.iff')
	mobileTemplate.setCreatureName('fynock')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(40)
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('fynock', mobileTemplate)
	