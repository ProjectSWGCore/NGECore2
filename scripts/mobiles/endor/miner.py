import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()

	mobileTemplate.setCreatureName('miner')
	mobileTemplate.setLevel(85)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup("death watch")
	mobileTemplate.setAssistRange(1)
	mobileTemplate.setStalker(False)
	mobileTemplate.setOptionsBitmask(128)

	templates = Vector()
	templates.add('object/mobile/shared_dressed_mand_miner_aqualish.iff')
	templates.add('object/mobile/shared_dressed_mand_miner_bith.iff')
	templates.add('object/mobile/shared_dressed_mand_miner_hum_01.iff')
	templates.add('object/mobile/shared_dressed_mand_miner_hum_02.iff')
	templates.add('object/mobile/shared_dressed_mand_miner_hum_03.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/pistol/shared_pistol_d18.iff', WeaponType.CARBINE, 1.0, 15, 'energy')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedshot')
	mobileTemplate.setAttacks(attacks)
	
	lootPoolNames_1 = ['Junk']
	lootPoolChances_1 = [100]
	lootGroupChance_1 = 100
	mobileTemplate.addToLootGroups(lootPoolNames_1,lootPoolChances_1,lootGroupChance_1)
	
	core.spawnService.addMobileTemplate('miner', mobileTemplate)
	return