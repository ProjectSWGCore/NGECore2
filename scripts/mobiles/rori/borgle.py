import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	attacks = Vector()
	attacks.add('bm_health_leech_1')
	attacks.add('bm_puncture_1')
	attacks.add('bm_wind_buffet_3')
	mobileTemplates.setAttacks(attacks)
	templates = Vector()
	templates.add('object/mobile/shared_borgle.iff')
	mobileTemplate.setCreatureName('borgle')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(25)
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('borgle', mobileTemplate)
	