import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()

	mobileTemplate.setCreatureName('drall_patriot_conqueror')
	mobileTemplate.setLevel(38)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup("drall")
	mobileTemplate.setAssistRange(6)
	mobileTemplate.setStalker(True)
	mobileTemplate.setOptionsBitmask(Options.AGGRESSIVE | Options.ATTACKABLE)

	templates = Vector()
	templates.add('object/mobile/shared_drall_female.iff')
	templates.add('object/mobile/shared_drall_male.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/carbine/shared_carbine_cdef.iff', WeaponType.CARBINE, 1.0, 15, 'energy')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShot')
	mobileTemplate.setAttacks(attacks)
	
	lootPoolNames_1 = ['Junk']
	lootPoolChances_1 = [100]
	lootGroupChance_1 = 100
	mobileTemplate.addToLootGroups(lootPoolNames_1,lootPoolChances_1,lootGroupChance_1)
	
	core.spawnService.addMobileTemplate('drall_patriot_conqueror', mobileTemplate)
	return