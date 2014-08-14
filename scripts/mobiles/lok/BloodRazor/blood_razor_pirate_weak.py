import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()

	mobileTemplate.setCreatureName('blood_razor_pirate_weak')
	mobileTemplate.setLevel(37)
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
	templates.add('object/mobile/shared_dressed_blood_razor_pirate_weak_wee_m.iff')
	templates.add('object/mobile/shared_dressed_blood_razor_pirate_weak_hum_f.iff')
	templates.add('object/mobile/shared_dressed_blood_razor_pirate_weak_hum_m.iff')
	templates.add('object/mobile/shared_dressed_blood_razor_pirate_weak_nikto_m.iff')
	templates.add('object/mobile/shared_dressed_blood_razor_pirate_weak_rod_m.iff')
	templates.add('object/mobile/shared_dressed_blood_razor_pirate_weak_zab_m.iff')
	mobileTemplate.setTemplates(templates)


	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/polearm/shared_polearm_lance_electric_polearm.iff', WeaponType.POLEARMMELEE, 1.5, 6, 'kinetic')
	
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	attacks = Vector()
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	
	mobileTemplate.setAttacks(attacks)
	
	lootPoolNames_1 = ['Junk']
	lootPoolChances_1 = [100]
	lootGroupChance_1 = 100
	mobileTemplate.addToLootGroups(lootPoolNames_1,lootPoolChances_1,lootGroupChance_1)
	
	
	
	core.spawnService.addMobileTemplate('blood_razor_pirate_weak', mobileTemplate)
	return