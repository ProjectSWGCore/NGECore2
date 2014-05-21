import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('eow_force_sensitive_crypt_crawler')
	mobileTemplate.setMinLevel(60)
	mobileTemplate.setMaxLevel(70)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(6)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(7)
	mobileTemplate.setMinSpawnDistance(3)
	mobileTemplate.setMaxSpawnDistance(5)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setSocialGroup('remnants of kun')
	mobileTemplate.setRespawnTime(300)
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_force_sensitive_crypt_crawler.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/polearm/shared_lance_staff_wood_s1.iff', 7, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('meleehit')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('force_sensitive_crypt_crawler', mobileTemplate)
	