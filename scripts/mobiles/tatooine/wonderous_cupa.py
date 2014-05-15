import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('cu_pa_wonderous')
	mobileTemplate.setLevel(12)
	mobileTemplate.setMinLevel(11)
	mobileTemplate.setMaxLevel(13)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(5)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Reptilian Meat")
	mobileTemplate.setMeatAmount(215)
	mobileTemplate.setHideType("Bristly Hide")
	mobileTemplate.setBoneAmount(100)	
	mobileTemplate.setBoneType("Animal Bone")
	mobileTemplate.setHideAmount(115)
	mobileTemplate.setSocialGroup("cu pa")
	mobileTemplate.setAssistRange(0)
	mobileTemplate.setStalker(False)	
	
	templates = Vector()
	templates.add('object/mobile/shared_cu_pa.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('wonderous_cu_pa', mobileTemplate)
	return