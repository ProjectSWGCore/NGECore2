import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('dark_force_crystal_hunter')
	mobileTemplate.setLevel(90)
	mobileTemplate.setMinLevel(60)
	mobileTemplate.setMaxLevel(69)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(6)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(4)
	mobileTemplate.setMinSpawnDistance(2)
	mobileTemplate.setMaxSpawnDistance(4)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setSocialGroup('remnants of kun')
	mobileTemplate.setAssistRange(12)
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_dark_force_crystal_hunter.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/sword/shared_sword_01.iff', 4, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('meleehit')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('dark_force_crystal_hunter', mobileTemplate)
	