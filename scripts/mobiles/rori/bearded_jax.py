import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	attacks = Vector()
	mobileTemplates.setAttacks(attacks)
	templates = Vector()
	templates.add('object/mobile/shared_bearded_jax.iff')
	mobileTemplate.setCreatureName('bearded jax')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(37)
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('bearded_jax', mobileTemplate)
	