import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	attacks = Vector()
	templates = Vector()
	mobileTemplates.setAttacks(attacks)
	templates.add('object/mobile/shared_narglatch_hue.iff')
	mobileTemplate.setCreatureName('narglatch')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(21)
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('narglatch', mobileTemplate)
	