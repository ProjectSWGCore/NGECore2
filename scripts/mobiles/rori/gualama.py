import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	attacks = Vector()
	attacks.add('bm_bite_3')
	attacks.add('bm_defensive_3')
	attacks.add('bm_kick_3')
	mobileTemplates.setAttacks(attacks)
	templates = Vector()
	templates.add('object/mobile/shared_gualama.iff')
	mobileTemplate.setCreatureName('gualama')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(27)
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('gualama', mobileTemplate)
	