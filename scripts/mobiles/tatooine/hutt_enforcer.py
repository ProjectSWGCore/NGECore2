import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('hutt_enforcer')
	mobileTemplate.setLevel(22)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(5)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(4)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup("jabba")
	mobileTemplate.setAssistRange(4)
	mobileTemplate.setStalker(True)
	
	templates = Vector()
	templates.add('object/mobile/shared_hutt_male.iff')
	templates.add('object/mobile/shared_hutt_female.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/sword/shared_sword_01.iff', 4, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('saberhit')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('hutt_enforcer', mobileTemplate)
	return