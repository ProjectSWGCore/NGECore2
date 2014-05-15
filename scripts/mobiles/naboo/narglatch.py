import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	attacks = Vector()
	add.attack('bm_claw_2')
	add.attack('bm_slash_2')
	templates = Vector()
	mobileTemplates.setAttacks(attacks)
	templates.add('object/mobile/shared_narglatch_hue.iff')
	mobileTemplate.setCreatureName('narglatch')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(21)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('narglatch', mobileTemplate)
	