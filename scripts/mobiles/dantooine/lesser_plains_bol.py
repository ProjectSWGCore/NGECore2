import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()	
	mobileTemplate.setCreatureName('lesser_plains_bol')
	mobileTemplate.setLevel(60)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(5)
	mobileTemplate.setMaxSpawnDistance(10)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Wild Meat")
	mobileTemplate.setMeatAmount(0)
	mobileTemplate.setHideType("Leathery Hide")
	mobileTemplate.setBoneAmount(300)	
	mobileTemplate.setBoneType("Animal Bone")
	mobileTemplate.setHideAmount(180)
	mobileTemplate.setSocialGroup("bol")
	mobileTemplate.setAssistRange(12)
	mobileTemplate.setStalker(False)	
	mobileTemplate.setOptionsBitmask(Options.ATTACKABLE)

	templates = Vector()
	templates.add('object/mobile/shared_bol_hue.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', WeaponType.UNARMED, 1.0, 6, 'kinetic')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	attacks = Vector()
	attacks.add('bm_bite_4')
	attacks.add('bm_charge_4')
	attacks.add('bm_dampen_pain_4')
	attacks.add('bm_stomp_4')
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('lesser_plains_bol', mobileTemplate)
	return