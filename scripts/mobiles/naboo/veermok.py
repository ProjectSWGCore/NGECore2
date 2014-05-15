import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	attacks.add('bm_dampen_pain_2')
	attacks.add('bm_stomp_2')
	mobileTemplates.setAttacks(attacks)
	templates.add('object/mobile/shared_veermok_hue.iff')
	mobileTemplate.setCreatureName('veermok')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(22)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('veermok', mobileTemplate)
	