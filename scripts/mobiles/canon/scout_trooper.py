import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('scout_trooper')
	mobileTemplate.setLevel(90)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(24)
	mobileTemplate.setAttackSpeed(0.4)
	mobileTemplate.setWeaponType(1)
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_scout_trooper_m.iff')
	mobileTemplate.setTemplates(templates)
		
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/pistol/shared_pistol_scout_blaster.iff', 0, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShot')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('scout_trooper', mobileTemplate)
	
