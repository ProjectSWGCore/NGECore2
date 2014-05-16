import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('dewback_cannibal')
	mobileTemplate.setLevel(13)
	mobileTemplate.setMinLevel(13)
	mobileTemplate.setMaxLevel(14)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(5)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Reptilian Meat")
	mobileTemplate.setMeatAmount(340)
	mobileTemplate.setHideType("Leathery Hide")
	mobileTemplate.setBoneAmount(260)	
	mobileTemplate.setBoneType("Animal Bones")
	mobileTemplate.setHideAmount(185)
	mobileTemplate.setSocialGroup("dewback cannibal")
	mobileTemplate.setAssistRange(10)
	mobileTemplate.setStalker(True)
	
	templates = Vector()
	templates.add('object/mobile/shared_dewback.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	attacks.add('bm_bite_1')
	attacks.add('bm_dampen_pain_1')
	attacks.add('bm_charge_1')
	attacks.add('bm_stomp_1')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('cannibal_dewback', mobileTemplate)
	return