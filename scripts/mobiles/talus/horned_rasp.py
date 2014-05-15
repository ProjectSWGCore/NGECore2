import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	attacks = Vector()
	attacks.add('bm_claw_3')
	attacks.add('bm_slash_3')
	attacks.add('bm_wind_buffet_3')
	mobileTemplates.setAttacks(attacks)
	templates = Vector()
	templates.add('object/mobile/shared_horned_rasp.iff')
	mobileTemplate.setCreatureName('horned rasp')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(45)
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('horned_rasp', mobileTemplate)
	