import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('bocatt_tormented')
	mobileTemplate.setLevel(18)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Reptilian Meat")
	mobileTemplate.setMeatAmount(100)
	mobileTemplate.setHideType("Leathery Hide")
	mobileTemplate.setBoneAmount(60)	
	mobileTemplate.setBoneType("Animal Bones")
	mobileTemplate.setHideAmount(35)
	mobileTemplate.setSocialGroup("bocatt")
	mobileTemplate.setAssistRange(10)
	mobileTemplate.setStalker(True)
	mobileTemplate.setOptionsBitmask(Options.AGGRESSIVE | Options.ATTACKABLE)
		
	templates = Vector()
	templates.add('object/mobile/shared_bocatt.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/creature/shared_creature_spit_small_toxicgreen.iff', WeaponType.UNARMED, 1.0, 6, 'kinetic')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	attacks.add('bm_bite_1')
	attacks.add('bm_bolster_armor_1')
	attacks.add('bm_disease_1')
	attacks.add('bm_enfeeble_1')
	mobileTemplate.setDefaultAttack('creatureRangedAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('tormented_bocatt', mobileTemplate)
	return