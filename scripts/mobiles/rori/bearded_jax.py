import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	attacks = Vector()
	attacks.add('bm_claw_3')
	attacks.add('bm_flank_1')
	attacks.add('bm_slash_3')
	mobileTemplates.setAttacks(attacks)
	templates = Vector()
	templates.add('object/mobile/shared_bearded_jax.iff')
	mobileTemplate.setCreatureName('bearded jax')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(24)
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('bearded_jax', mobileTemplate)
	