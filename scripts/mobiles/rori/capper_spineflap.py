import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/shared_capper_spineflap.iff')
	mobileTemplate.setCreatureName('capper spineflap')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(31)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('capper_spineflap', mobileTemplate)
	