import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	attacks = Vector()
	attacks.add('bm_hamstring_3')
	attacks.add('bm_bite_3')
	mobileTemplates.setAttacks(attacks)
	templates = Vector()
	templates.add('object/mobile/shared_pugoriss.iff')
	mobileTemplate.setCreatureName('pugoriss')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(25)
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('pugoriss', mobileTemplate)
	