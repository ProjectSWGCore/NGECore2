import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('dragonet')
	mobileTemplate.setLevel(24)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Carnivore Meat")
	mobileTemplate.setMeatAmount(125)
	mobileTemplate.setHideType("Bristly Hide")
	mobileTemplate.setBoneAmount(75)	
	mobileTemplate.setBoneType("Animal Bone")
	mobileTemplate.setHideAmount(68)
	mobileTemplate.setSocialGroup("dragonet")
	mobileTemplate.setAssistRange(10)
	mobileTemplate.setStalker(True)	
	mobileTemplate.setOptionsBitmask(Options.AGGRESSIVE | Options.ATTACKABLE)
	
	templates = Vector()
	templates.add('object/mobile/shared_dragonet.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/creature/shared_creature_spit_small_toxicgreen.iff', WeaponType.UNARMED, 1.0, 6, 'kinetic')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	attacks.add('bm_bite_2')
	attacks.add('bm_bolster_armor_2')
	attacks.add('bm_disease_2')
	attacks.add('bm_enfeeble_2')
	mobileTemplate.setDefaultAttack('creatureRangedAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('dragonet', mobileTemplate)
	return