import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/shared_guf_drolg.iff')
	mobileTemplate.setCreatureName('guf drolg')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(42)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('guf_drolg', mobileTemplate)
	