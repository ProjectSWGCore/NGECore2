import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	attacks = Vector()
	mobileTemplates.setAttacks(attacks)
	templates = Vector()
	templates.add('object/mobile/shared_bark_mite.iff')
	mobileTemplate.setCreatureName('bark mite')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(37)
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('bark_mite', mobileTemplate)
	