import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()	
	mobileTemplate.setCreatureName('grassland_voritor_tracker')
	mobileTemplate.setLevel(69)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(5)
	mobileTemplate.setMaxSpawnDistance(10)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Carnivore Meat")
	mobileTemplate.setMeatAmount(45)
	mobileTemplate.setHideType("Leathery Hide")
	mobileTemplate.setBoneAmount(35)	
	mobileTemplate.setBoneType("Avian Bone")
	mobileTemplate.setHideAmount(40)
	mobileTemplate.setSocialGroup("voritor lizard")
	mobileTemplate.setAssistRange(12)
	mobileTemplate.setStalker(True)	
	mobileTemplate.setOptionsBitmask(Options.AGGRESSIVE | Options.ATTACKABLE)

	templates = Vector()
	templates.add('object/mobile/shared_grassland_voritor_tracker.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', WeaponType.UNARMED, 1.0, 6, 'kinetic')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('grassland_voritor_tracker', mobileTemplate)
	return