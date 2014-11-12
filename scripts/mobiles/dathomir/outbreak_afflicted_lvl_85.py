import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('outbreak_afflicted_lvl_85')
	mobileTemplate.setLevel(85)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)
	
	mobileTemplate.setMinSpawnDistance(5)
	mobileTemplate.setMaxSpawnDistance(10)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setSocialGroup('undead')
	mobileTemplate.setAssistRange(15)
	mobileTemplate.setRespawnTime(300)
	mobileTemplate.setOptionsBitmask(Options.AGGRESSIVE | Options.ATTACKABLE)
	
	templates = Vector()
	templates.add('object/mobile/shared_outbreak_undead_deathtrooper_01_f.iff')
	templates.add('object/mobile/shared_outbreak_undead_deathtrooper_01_m.iff')
	templates.add('object/mobile/shared_outbreak_undead_deathtrooper_02_f.iff')
	templates.add('object/mobile/shared_outbreak_undead_deathtrooper_03_m.iff')
	templates.add('object/mobile/shared_outbreak_undead_deathtrooper_04_m.iff')
	templates.add('object/mobile/shared_outbreak_undead_deathtrooper_05_m.iff')
	templates.add('object/mobile/shared_outbreak_undead_deathtrooper_06_m.iff')
	templates.add('object/mobile/shared_outbreak_undead_deathtrooper_07_m.iff')
	templates.add('object/mobile/shared_outbreak_undead_deathtrooper_08_m.iff')
	templates.add('object/mobile/shared_outbreak_undead_deathtrooper_09_m.iff')
	templates.add('object/mobile/shared_outbreak_undead_deathtrooper_10_m.iff')
	templates.add('object/mobile/shared_outbreak_undead_deathtrooper_11_m.iff')
	templates.add('object/mobile/shared_outbreak_undead_deathtrooper_12_m.iff')
	templates.add('object/mobile/shared_outbreak_undead_deathtrooper_13_m.iff')
	templates.add('object/mobile/shared_outbreak_undead_deathtrooper_14_m.iff')
	templates.add('object/mobile/shared_outbreak_undead_deathtrooper_15_m.iff')
	templates.add('object/mobile/shared_outbreak_undead_deathtrooper_16_m.iff')
	templates.add('object/mobile/shared_outbreak_undead_deathtrooper_17_m.iff')
	templates.add('object/mobile/shared_outbreak_undead_deathtrooper_18_m.iff')
	templates.add('object/mobile/shared_outbreak_undead_deathtrooper_19_m.iff')
	templates.add('object/mobile/shared_outbreak_undead_deathtrooper_20_m.iff')
	templates.add('object/mobile/shared_outbreak_undead_deathtrooper_21_m.iff')
	templates.add('object/mobile/shared_outbreak_undead_deathtrooper_22_m.iff')
	templates.add('object/mobile/shared_outbreak_undead_deathtrooper_23_m.iff')
	templates.add('object/mobile/shared_outbreak_undead_deathtrooper_24_m.iff')
	templates.add('object/mobile/shared_outbreak_undead_deathtrooper_25_m.iff')
	templates.add('object/mobile/shared_outbreak_undead_deathtrooper_26_m.iff')
	templates.add('object/mobile/shared_outbreak_undead_deathtrooper_27_m.iff')
	templates.add('object/mobile/shared_outbreak_undead_deathtrooper_28_m.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/rifle/shared_rifle_e11.iff', WeaponType.RIFLE, 0.8, 0, 'energy')
	weaponTemplates.add(weapontemplate)
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', WeaponType.ONEHANDEDMELEE, 1.0, 4, 'kinetic')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('RangedShot')
	mobileTemplate.setDefaultAttack('MeleeHit')
	mobileTemplate.setAttacks(attacks)
	
	lootPoolNames_1 = ['Junk']
	lootPoolChances_1 = [100]
	lootGroupChance_1 = 65
	mobileTemplate.addToLootGroups(lootPoolNames_1,lootPoolChances_1,lootGroupChance_1)
	
	lootPoolNames_4 = ['random_stat_jewelry']
	lootPoolChances_4 = [100]
	lootGroupChance_4 = 8
	mobileTemplate.addToLootGroups(lootPoolNames_4,lootPoolChances_4,lootGroupChance_4)
	
	core.spawnService.addMobileTemplate('outbreak_afflicted_lvl_85', mobileTemplate)

	return