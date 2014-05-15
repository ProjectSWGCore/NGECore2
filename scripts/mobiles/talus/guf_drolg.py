import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	attacks = Vector()
	attacks.add('bm_bite_3')
	attacks.add('bm_charge_3')
	attacks.add('bm_dampen_pain_3')
	attacks.add('bm_stomp_3')
	mobileTemplates.setAttacks(attacks)
	templates = Vector()
	templates.add('object/mobile/shared_guf_drolg.iff')
	mobileTemplate.setCreatureName('guf drolg')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(48)
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('guf_drolg', mobileTemplate)
	