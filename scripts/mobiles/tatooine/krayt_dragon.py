import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	templates = Vector()
	templates.add('object/mobile/shared_krayt_dragon.iff')
	mobileTemplate.setTemplates(templates)
	mobileTemplate.setLevel(10)
	mobileTemplate.setDifficulty(2)
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	mobileTemplate.setCreatureName('krayt_dragon')
	mobileTemplate.setScale(2)
	mobileTemplate.setAttackRange(12)
	core.spawnService.addMobileTemplate('krayt_dragon', mobileTemplate)
	