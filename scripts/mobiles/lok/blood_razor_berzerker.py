import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()

	mobileTemplate.setCreatureName('blood_razor_berzerker')
	mobileTemplate.setLevel(38)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup("bloodrazor")
	mobileTemplate.setAssistRange(8)
	mobileTemplate.setStalker(True)
	mobileTemplate.setOptionsBitmask(Options.AGGRESSIVE | Options.ATTACKABLE)

	templates = Vector()
	templates.add('object/mobile/shared_dressed_blood_razor_pirate_berzerker_hum_f.iff')
	templates.add('object/mobile/shared_dressed_blood_razor_pirate_berzerker_hum_m.iff')
	templates.add('object/mobile/shared_dressed_blood_razor_pirate_berzerker_rod_m.iff')
	templates.add('object/mobile/shared_dressed_blood_razor_pirate_berzerker_tran_m.iff')
	templates.add('object/mobile/shared_dressed_blood_razor_pirate_officer_wee_m.iff')
	mobileTemplate.setTemplates(templates)


	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/pistol/shared_pistol_power5.iff', WeaponType.PISTOL, 3.2, 6, 'energy')
	
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShot')
	
	mobileTemplate.setAttacks(attacks)
	
	lootPoolNames_1 = ['Junk']
	lootPoolChances_1 = [100]
	lootGroupChance_1 = 100
	mobileTemplate.addToLootGroups(lootPoolNames_1,lootPoolChances_1,lootGroupChance_1)
	
	
	
	core.spawnService.addMobileTemplate('blood_razor_berzerker', mobileTemplate)
	return