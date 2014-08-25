import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('tusken_raider_zealot')
	mobileTemplate.setLevel(5)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup("tusken raider")
	mobileTemplate.setAssistRange(4)
	mobileTemplate.setStalker(False)
	mobileTemplate.setOptionsBitmask(Options.AGGRESSIVE | Options.ATTACKABLE)
	
	templates = Vector()
	templates.add('object/mobile/shared_tusken_raider.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/rifle/shared_rifle_tusken_generic.iff', WeaponType.RIFLE, 1.0, 24, 'kinetic')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShotrifle')
	mobileTemplate.setAttacks(attacks)
	
	lootPoolNames_1 = ['Junk']
	lootPoolChances_1 = [100]
	lootGroupChance_1 = 65
	mobileTemplate.addToLootGroups(lootPoolNames_1,lootPoolChances_1,lootGroupChance_1)
	
	lootPoolNames_2 = ['batons','random_loot_rifles']
	lootPoolChances_2 = [60,40]
	lootGroupChance_2 = 20
	mobileTemplate.addToLootGroups(lootPoolNames_2,lootPoolChances_2,lootGroupChance_2)
	
	lootPoolNames_3 = ['tusken_raider_clothing']
	lootPoolChances_3 = [100]
	lootGroupChance_3 = 10
	mobileTemplate.addToLootGroups(lootPoolNames_3,lootPoolChances_3,lootGroupChance_3)
	
	lootPoolNames_4 = ['Colorcrystals']
	lootPoolChances_4 = [100]
	lootGroupChance_4 = 6
	mobileTemplate.addToLootGroups(lootPoolNames_4,lootPoolChances_4,lootGroupChance_4)
	
	core.spawnService.addMobileTemplate('tusken_zealot', mobileTemplate)
	return