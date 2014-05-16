import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	attacks = Vector()
	mobileTemplates.setAttacks(attacks)
	templates = Vector()
	templates.add('object/mobile/shared_huf_dun.iff')
	mobileTemplate.setCreatureName('huf dun')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(47)
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('huf_dun', mobileTemplate)
	