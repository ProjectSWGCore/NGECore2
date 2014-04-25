import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('rancor')
	mobileTemplate.setLevel(50)
	mobileTemplate.setDifficulty(1)
	mobileTemplate.setAttackRange(5)
	
	templates = Vector()
	templates.add('object/mobile/shared_rancor.iff')
	mobileTemplate.setTemplates(templates)
	
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('rancor', mobileTemplate)