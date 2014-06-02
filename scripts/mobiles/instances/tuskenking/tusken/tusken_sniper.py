import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('heroic_tusken_sniper')
	mobileTemplate.setLevel(90)
	mobileTemplate.setDifficulty(1)
	mobileTemplate.setAttackRange(12)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(0)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup("heroic tusken")
	mobileTemplate.setAssistRange(12)
	mobileTemplate.setStalker(True)
	mobileTemplate.setOptionsBitmask(192)
	
	templates = Vector()
	templates.add('object/mobile/shared_tusken_raider.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/rifle/shared_rifle_tusken.iff', 0, 1.0)
	weapontemplate1 = WeaponTemplate('object/weapon/melee/baton/shared_baton_gaderiffi_elite.iff', 4, 1.0)
	weaponTemplates.add(weapontemplate)
	weaponTemplates.add(weapontemplate1)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShotrifle')
	attacks.add('meleeHit')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('heroic_tusken_sniper', mobileTemplate)
	return