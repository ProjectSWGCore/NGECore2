import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	attacks = Vector()
	attacks.add('bm_bite_3')
	attacks.add('bm_bolster_armor_3')
	attacks.add('bm_enfeeble_3')
	mobileTemplates.setAttacks(attacks)
	templates = Vector()
	templates.add('object/mobile/shared_bark_mite.iff')
	mobileTemplate.setCreatureName('bark mite')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(27)
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('bark_mite', mobileTemplate)
	