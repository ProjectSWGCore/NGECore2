import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('dwarf_bantha')
	mobileTemplate.setLevel(10)
	mobileTemplate.setMinLevel(10)
	mobileTemplate.setMaxLevel(11)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(5)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(6)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Herbivore Meat")
	mobileTemplate.setMeatAmount(300)
	mobileTemplate.setHideType("Wooly Hide")
	mobileTemplate.setBoneAmount(200)	
	mobileTemplate.setBoneType("Animal Bone")
	mobileTemplate.setHideAmount(100)
	mobileTemplate.setMilkType("Domesticated Milk")
	mobileTemplate.setMilkAmount(50)
	mobileTemplate.setSocialGroup("bantha")
	mobileTemplate.setAssistRange(0)
	mobileTemplate.setStalker(False)	
	
	templates = Vector()
	templates.add('object/mobile/shared_dwarf_bantha.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', 6, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	attacks.add('bm_bite_1')
	attacks.add('bm_charge_1')
	attacks.add('bm_dampen_pain_1')
	attacks.add('bm_stomp_1')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('dwarf_bantha', mobileTemplate)
	return