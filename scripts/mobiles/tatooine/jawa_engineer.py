import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('jawa_engineer')
	mobileTemplate.setLevel(16)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(12)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(2)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup("jabba")
	mobileTemplate.setAssistRange(0)
	mobileTemplate.setStalker(False)
	
	templates = Vector()
	templates.add('object/mobile/shared_jawa.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/pistol/shared_pistol_cdef.iff', 2, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedshotpistol')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('jawa_engineer', mobileTemplate)
	return