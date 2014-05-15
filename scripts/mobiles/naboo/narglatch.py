import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	attacks.add('bm_claw_2')
	attacks.add('bm_slash_2')
	mobileTemplates.setAttacks(attacks)
	templates.add('object/mobile/shared_narglatch_hue.iff')
	mobileTemplate.setCreatureName('narglatch')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(21)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('narglatch', mobileTemplate)
	