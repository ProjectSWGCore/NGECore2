import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	attacks = Vector()
	attacks.add('bm_flank_1')
	attacks.add('bm_health_leech_2')
	attacks.add('bm_wind_buffet_3')
	mobileTemplates.setAttacks(attacks)
	templates = Vector()
	templates.add('object/mobile/shared_fynock.iff')
	mobileTemplate.setCreatureName('fynock')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(46)
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('fynock', mobileTemplate)
	