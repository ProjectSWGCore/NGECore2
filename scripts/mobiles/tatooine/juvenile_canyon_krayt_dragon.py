import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('juvenile_canyon_krayt')
	mobileTemplate.setLevel(82)
	mobileTemplate.setDifficulty(2)
	mobileTemplate.setAttackRange(12)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setAttackSpeed(1.0)
	
	templates = Vector()
	templates.add('object/mobile/shared_juvenile_canyon_krayt.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('juvenile_canyon_krayt_dragon', mobileTemplate)
	return