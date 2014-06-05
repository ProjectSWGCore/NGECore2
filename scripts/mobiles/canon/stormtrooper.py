import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('stormtrooper')
	mobileTemplate.setLevel(90)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(24)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(0)
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_stormtrooper_m.iff')
	mobileTemplate.setTemplates(templates)
		
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/rifle/shared_rifle_e11.iff', 0, 1.0)
	weaponTemplates.add(weapontemplate)
	weapontemplate = WeaponTemplate('object/weapon/ranged/rifle/shared_rifle_t21.iff', 0, 1.0)
	weaponTemplates.add(weapontemplate)	
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShot')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('stormtrooper', mobileTemplate)
	