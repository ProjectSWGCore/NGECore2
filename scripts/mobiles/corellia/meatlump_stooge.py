import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()

	mobileTemplate.setCreatureName('meatlump_stooge')
	mobileTemplate.setLevel(27)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup("meatlump")
	mobileTemplate.setAssistRange(6)
	mobileTemplate.setStalker(True)
	mobileTemplate.setOptionsBitmask(128)

	templates = Vector()
	templates.add('object/mobile/shared_dressed_meatlump_female_01.iff')
	templates.add('object/mobile/shared_dressed_meatlump_female_02.iff')
	templates.add('object/mobile/shared_dressed_meatlump_female_03.iff')
	templates.add('object/mobile/shared_dressed_meatlump_female_04.iff')
	templates.add('object/mobile/shared_dressed_meatlump_female_05.iff')
	templates.add('object/mobile/shared_dressed_meatlump_female_06.iff')
	templates.add('object/mobile/shared_dressed_meatlump_female_07.iff')
	templates.add('object/mobile/shared_dressed_meatlump_male_01.iff')
	templates.add('object/mobile/shared_dressed_meatlump_male_02.iff')
	templates.add('object/mobile/shared_dressed_meatlump_male_03.iff')
	templates.add('object/mobile/shared_dressed_meatlump_male_04.iff')
	templates.add('object/mobile/shared_dressed_meatlump_male_05.iff')
	templates.add('object/mobile/shared_dressed_meatlump_male_06.iff')
	templates.add('object/mobile/shared_dressed_meatlump_male_07.iff')
	templates.add('object/mobile/shared_dressed_meatlump_male_08.iff')
	templates.add('object/mobile/shared_dressed_meatlump_male_09.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/carbine/shared_carbine_e5.iff', WeaponType.CARBINE, 1.0, 15, 'energy')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShot')
	mobileTemplate.setAttacks(attacks)
	
	lootPoolNames_1 = ['Junk']
	lootPoolChances_1 = [100]
	lootGroupChance_1 = 100
	mobileTemplate.addToLootGroups(lootPoolNames_1,lootPoolChances_1,lootGroupChance_1)
	
	core.spawnService.addMobileTemplate('meatlump_stooge', mobileTemplate)
	return