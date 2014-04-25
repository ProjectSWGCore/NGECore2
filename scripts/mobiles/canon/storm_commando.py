import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('storm_commando')
	mobileTemplate.setLevel(71)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(24)
	mobileTemplate.setAttackSpeed(0.4)
	mobileTemplate.setWeaponType(1)
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_scout_trooper_black_black.iff')
	mobileTemplate.setTemplates(templates)
		
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/pistol/shared_pistol_scout_blaster.iff', 0, 1.0)
	weaponTemplates.add(weapontemplate)
	weapontemplate = WeaponTemplate('object/weapon/ranged/pistol/shared_pistol_dx2.iff', 0, 1.0)
	weaponTemplates.add(weapontemplate)	
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShot')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('storm_commando', mobileTemplate)
	
