import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()

	mobileTemplate.setCreatureName('berserk_marauder')
	mobileTemplate.setLevel(78)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup("marauder")
	mobileTemplate.setAssistRange(12)
	mobileTemplate.setStalker(True)
	mobileTemplate.setOptionsBitmask(Options.AGGRESSIVE | Options.ATTACKABLE)

	templates = Vector()
	templates.add('object/mobile/shared_marauder_male_01.iff')
	templates.add('object/mobile/shared_marauder_male_02.iff')
	templates.add('object/mobile/shared_marauder_male_02.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/polearm/shared_polearm_vibro_axe.iff', WeaponType.TWOHANDEDMELEE, 1.0, 5, 'kinetic')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	attacks = Vector()
	mobileTemplate.setDefaultAttack('meleeHit')
	mobileTemplate.setAttacks(attacks)
	
	lootPoolNames_1 = ['Junk']
	lootPoolChances_1 = [100]
	lootGroupChance_1 = 100
	mobileTemplate.addToLootGroups(lootPoolNames_1,lootPoolChances_1,lootGroupChance_1)
	
	core.spawnService.addMobileTemplate('berserk_marauder', mobileTemplate)
	return