import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/shared_kaadu_hue.iff')
	mobileTemplate.setCreatureName('kaadu')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(14)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('kaadu', mobileTemplate)