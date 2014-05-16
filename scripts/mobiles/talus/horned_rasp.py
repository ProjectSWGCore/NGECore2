import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	attacks = Vector()
	mobileTemplates.setAttacks(attacks)
	templates = Vector()
	templates.add('object/mobile/shared_horned_rasp.iff')
	mobileTemplate.setCreatureName('horned rasp')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(34)
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('horned_rasp', mobileTemplate)
	