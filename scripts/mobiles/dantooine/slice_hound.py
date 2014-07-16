import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()	
	mobileTemplate.setCreatureName('slice_hound')
	mobileTemplate.setLevel(51)
	mobileTemplate.setMinLevel(51)
	mobileTemplate.setMaxLevel(51)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(5)
	mobileTemplate.setMaxSpawnDistance(10)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Carnivore Meat")
	mobileTemplate.setMeatAmount(65)
	mobileTemplate.setHideType("Bristley Hide")
	mobileTemplate.setBoneAmount(35)	
	mobileTemplate.setBoneType("Animal Bone")
	mobileTemplate.setHideAmount(30)
	mobileTemplate.setSocialGroup("slice_hound")
	mobileTemplate.setAssistRange(1)
	mobileTemplate.setStalker(False)	
	mobileTemplate.setOptionsBitmask(Options.ATTACKABLE)

	templates = Vector()
	templates.add('object/mobile/shared_dewback.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', WeaponType.UNARMED, 1.0, 6, 'kinetic')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	attacks = Vector()
	attacks.add('bm_bite_2')
	attacks.add('bm_hamstring_2')
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('slice_hound', mobileTemplate)
	return