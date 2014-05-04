import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/shared_durni.iff')
	mobileTemplate.setCreatureName('durni')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(4)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('durni', mobileTemplate)
	