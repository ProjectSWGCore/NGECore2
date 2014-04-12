import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/shared_gorax.iff')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(45)
	mobileTemplate.setDifficulty(3)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	mobileTemplate.setCreatureName('gorax')
	mobileTemplate.setScale(2)
	mobileTemplate.setAttackRange(12)
	core.spawnService.addMobileTemplate('gorax', mobileTemplate)