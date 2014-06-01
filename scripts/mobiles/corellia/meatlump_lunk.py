import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()

	mobileTemplate.setCreatureName('corellia_meatlump_lunk')
	mobileTemplate.setLevel(13)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(15)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(2)
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
	weapontemplate = WeaponTemplate('object/weapon/ranged/pistol/shared_pistol_d18.iff', 2, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShot')
	mobileTemplate.setAttacks(attacks)
	
	lootPoolNames_1 = ['Junk']
	lootPoolChances_1 = [100]
	lootGroupChance_1 = 100
	mobileTemplate.addToLootGroups(lootPoolNames_1,lootPoolChances_1,lootGroupChance_1)
	
	core.spawnService.addMobileTemplate('meatlump_lunk', mobileTemplate)
	return