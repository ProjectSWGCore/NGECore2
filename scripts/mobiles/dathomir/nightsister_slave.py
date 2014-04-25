import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('nightsister_slave')
	mobileTemplate.setLevel(86)
	mobileTemplate.setDifficulty(1)
	mobileTemplate.setAttackRange(5)
	mobileTemplate.setAttackSpeed(1.0)
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_dathomir_nightsister_slave.iff')
	mobileTemplate.setTemplates(templates)
	
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('meleehit')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('nightsister_slave', mobileTemplate)