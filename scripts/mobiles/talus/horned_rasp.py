import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/shared_horned_rasp.iff')
	mobileTemplate.setCreatureName('horned rasp')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(34)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('horned_rasp', mobileTemplate)
	