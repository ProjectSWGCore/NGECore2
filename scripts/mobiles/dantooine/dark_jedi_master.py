import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('dark_jedi_master')
	mobileTemplate.setLevel(90)
	mobileTemplate.setDifficulty(2)
	mobileTemplate.setAttackRange(6)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(9)
	mobileTemplate.setMinSpawnDistance(3)
	mobileTemplate.setMaxSpawnDistance(5)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setSocialGroup('dark jedi')
	mobileTemplate.setAssistRange(12)
	mobileTemplate.setRespawnTime(300)
	mobileTemplate.setOptionsBitmask(192)
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_dark_jedi_master_female_twk_01.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_master_female_twk_02.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_master_female_zab_01.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_master_female_zab_02.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_master_male_human_01.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_master_male_human_04.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_master_male_human_05.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_master_male_human_06.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_one_handed_gen5.iff', 9, 1.0)
	weaponTemplates.add(weapontemplate)
	weapontemplate = WeaponTemplate('object/weapon/melee/2h_sword/crafted_saber/shared_sword_lightsaber_two_handed_gen5.iff', 10, 1.0)
	weaponTemplates.add(weapontemplate)
	weapontemplate = WeaponTemplate('object/weapon/melee/polearm/crafted_saber/shared_sword_lightsaber_polearm_gen5.iff', 11, 1.0)
	weaponTemplates.add(weapontemplate)	
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('saberHit')
	mobileTemplate.setAttacks(attacks)


	lootPoolNames_1 = ['Junk']
	lootPoolChances_1 = [100]
	lootGroupChance_1 = 65
	mobileTemplate.addToLootGroups(lootPoolNames_1,lootPoolChances_1,lootGroupChance_1)
	
	lootPoolNames_2 = ['jedi_relic_1']
	lootPoolChances_2 = [100]
	lootGroupChance_2 = 85
	mobileTemplate.addToLootGroups(lootPoolNames_2,lootPoolChances_2,lootGroupChance_2)
	
	lootPoolNames_3 = ['powercrystals_hiq']
	lootPoolChances_3 = [100]
	lootGroupChance_3 = 12
	mobileTemplate.addToLootGroups(lootPoolNames_3,lootPoolChances_3,lootGroupChance_3)
	
	lootPoolNames_4 = ['random_stat_jewelry']
	lootPoolChances_4 = [100]
	lootGroupChance_4 = 8
	mobileTemplate.addToLootGroups(lootPoolNames_4,lootPoolChances_4,lootGroupChance_4)

	lootPoolNames_5 = ['sithholocrons']
	lootPoolChances_5 = [100]
	lootGroupChance_5 = 3
	mobileTemplate.addToLootGroups(lootPoolNames_5,lootPoolChances_5,lootGroupChance_5)
	
	core.spawnService.addMobileTemplate('dark_jedi_master', mobileTemplate)
	