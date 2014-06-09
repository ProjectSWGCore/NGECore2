import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('gondula_elder_worker')
	mobileTemplate.setLevel(78)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(3)
	mobileTemplate.setMaxSpawnDistance(5)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup('gondula tribe')
	mobileTemplate.setAssistRange(1)
	mobileTemplate.setOptionsBitmask(128)
	mobileTemplate.setStalker(True)
	
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_ewok_f_01.iff')
	templates.add('object/mobile/shared_dressed_ewok_f_02.iff')
	templates.add('object/mobile/shared_dressed_ewok_f_03.iff')
	templates.add('object/mobile/shared_dressed_ewok_f_04.iff')
	templates.add('object/mobile/shared_dressed_ewok_f_05.iff')
	templates.add('object/mobile/shared_dressed_ewok_f_06.iff')
	templates.add('object/mobile/shared_dressed_ewok_f_07.iff')
	templates.add('object/mobile/shared_dressed_ewok_f_08.iff')
	templates.add('object/mobile/shared_dressed_ewok_f_09.iff')
	templates.add('object/mobile/shared_dressed_ewok_f_10.iff')
	templates.add('object/mobile/shared_dressed_ewok_f_11.iff')
	templates.add('object/mobile/shared_dressed_ewok_f_12.iff')
	templates.add('object/mobile/shared_dressed_ewok_m_01.iff')
	templates.add('object/mobile/shared_dressed_ewok_m_02.iff')
	templates.add('object/mobile/shared_dressed_ewok_m_03.iff')
	templates.add('object/mobile/shared_dressed_ewok_m_04.iff')
	templates.add('object/mobile/shared_dressed_ewok_m_05.iff')
	templates.add('object/mobile/shared_dressed_ewok_m_06.iff')
	templates.add('object/mobile/shared_dressed_ewok_m_07.iff')
	templates.add('object/mobile/shared_dressed_ewok_m_08.iff')
	templates.add('object/mobile/shared_dressed_ewok_m_09.iff')
	templates.add('object/mobile/shared_dressed_ewok_m_10.iff')
	templates.add('object/mobile/shared_dressed_ewok_m_11.iff')
	templates.add('object/mobile/shared_dressed_ewok_m_12.iff')
	
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
	lootGroupChance_1 = 65
	mobileTemplate.addToLootGroups(lootPoolNames_1,lootPoolChances_1,lootGroupChance_1)
	
	lootPoolNames_2 = ['random_loot_primitives']
	lootPoolChances_2 = [100]
	lootGroupChance_2 = 35
	mobileTemplate.addToLootGroups(lootPoolNames_2,lootPoolChances_2,lootGroupChance_2)
	
	core.spawnService.addMobileTemplate('gondula_elder_worker', mobileTemplate)
	return