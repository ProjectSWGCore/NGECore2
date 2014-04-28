import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('tusken_raider')
	mobileTemplate.setLevel(10)
	mobileTemplate.setDifficulty(1)
	mobileTemplate.setAttackRange(24)
	mobileTemplate.setAttackSpeed(1.0)
	
	templates = Vector()
	templates.add('object/mobile/shared_tusken_raider.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/rifle/shared_rifle_tusken.iff', 0, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShot')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('tusken_raider', mobileTemplate)
	return