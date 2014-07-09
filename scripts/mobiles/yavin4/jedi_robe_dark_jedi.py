import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('col_jedi_robe_dark_jedi')
	mobileTemplate.setLevel(90)
	mobileTemplate.setDifficulty(Difficulty.BOSS)

	mobileTemplate.setMinSpawnDistance(3)
	mobileTemplate.setMaxSpawnDistance(5)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setSocialGroup('dark jedi')
	mobileTemplate.setAssistRange(12)
	mobileTemplate.setRespawnTime(300)
	mobileTemplate.setOptionsBitmask(Options.AGGRESSIVE | Options.ATTACKABLE)
	
	templates = Vector()
	templates.add('object/mobile/shared_jedi_robe_dark_jedi.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_one_handed_gen5.iff', WeaponType.ONEHANDEDSABER, 1.0, 6, 'energy')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('saberHit')
	attacks.add('fs_dm_7')
	attacks.add('fs_dm_cc_crit_5')
	attacks.add('fs_sweep_7')
	attacks.add('fs_maelstrom_5')
	attacks.add('fs_ae_dm_cc_6')
	attacks.add('col_jedi_statue_dark_debuff')
	mobileTemplate.setAttacks(attacks)


	lootPoolNames_1 = ['Junk']
	lootPoolChances_1 = [100]
	lootGroupChance_1 = 65
	mobileTemplate.addToLootGroups(lootPoolNames_1,lootPoolChances_1,lootGroupChance_1)
	
	lootPoolNames_2 = ['jedi_relic_1']
	lootPoolChances_2 = [100]
	lootGroupChance_2 = 30
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
	
	core.spawnService.addMobileTemplate('jedi_robe_dark_jedi', mobileTemplate)
	