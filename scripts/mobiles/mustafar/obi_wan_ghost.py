import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/som/shared_obi_wan_ghost.iff')
	mobileTemplate.setCreatureName('obi_wan_ghost')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(99)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShot')
	mobileTemplate.setAttacks(attacks)
	core.spawnService.addMobileTemplate('obi_wan_ghost', mobileTemplate)