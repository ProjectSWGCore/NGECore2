import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	attacks = Vector()
	attacks.add('bm_dampen_pain_3')
	attacks.add('bm_deflective_hide_1')
	attacks.add('bm_puncture_1')
	attacks.add('bm_shaken_3')
	attacks.add('bm_stomp_3')
	mobileTemplates.setAttacks(attacks)
	templates = Vector()
	templates.add('object/mobile/shared_torton.iff')
	mobileTemplate.setCreatureName('torton')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(26)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('torton', mobileTemplate)
	