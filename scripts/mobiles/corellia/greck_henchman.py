import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()

	mobileTemplate.setCreatureName('greck_henchman')
	mobileTemplate.setLevel(23)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup("olag greck")
	mobileTemplate.setAssistRange(6)
	mobileTemplate.setStalker(True)
	mobileTemplate.setOptionsBitmask(128)

	templates = Vector()
	templates.add('object/mobile/shared_greck_thug_f_01.iff')
	templates.add('object/mobile/shared_greck_thug_f_02.iff')
	templates.add('object/mobile/shared_greck_thug_f_03.iff')
	templates.add('object/mobile/shared_greck_thug_m_01.iff')
	templates.add('object/mobile/shared_greck_thug_m_02.iff')
	templates.add('object/mobile/shared_greck_thug_m_03.iff')
	templates.add('object/mobile/shared_greck_thug_m_04.iff')
	templates.add('object/mobile/shared_greck_thug_m_05.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/unarmed/shared_unarmed_default.iff', WeaponType.UNARMED, 1.0, 6, 'kinetic')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	attacks = Vector()
	mobileTemplate.setDefaultAttack('meleeHit')
	mobileTemplate.setAttacks(attacks)
	
	lootPoolNames_1 = ['Junk']
	lootPoolChances_1 = [100]
	lootGroupChance_1 = 100
	mobileTemplate.addToLootGroups(lootPoolNames_1,lootPoolChances_1,lootGroupChance_1)
	
	core.spawnService.addMobileTemplate('greck_henchman', mobileTemplate)
	return