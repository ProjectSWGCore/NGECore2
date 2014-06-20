import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()

	mobileTemplate.setCreatureName('selonian_healer')
	mobileTemplate.setLevel(38)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup("selonian")
	mobileTemplate.setAssistRange(6)
	mobileTemplate.setStalker(True)
	mobileTemplate.setOptionsBitmask(Options.AGGRESSIVE | Options.ATTACKABLE)

	templates = Vector()
	templates.add('object/mobile/shared_dressed_selonian_m_01.iff')
	templates.add('object/mobile/shared_dressed_selonian_m_02.iff')
	templates.add('object/mobile/shared_dressed_selonian_m_03.iff')
	templates.add('object/mobile/shared_dressed_selonian_m_04.iff')
	templates.add('object/mobile/shared_dressed_selonian_m_05.iff')
	templates.add('object/mobile/shared_dressed_selonian_m_06.iff')
	templates.add('object/mobile/shared_dressed_selonian_m_07.iff')
	templates.add('object/mobile/shared_dressed_selonian_m_08.iff')
	templates.add('object/mobile/shared_dressed_selonian_m_09.iff')
	templates.add('object/mobile/shared_dressed_selonian_m_10.iff')
	templates.add('object/mobile/shared_dressed_selonian_m_11.iff')
	templates.add('object/mobile/shared_dressed_selonian_m_12.iff')
	templates.add('object/mobile/shared_dressed_selonian_f_01.iff')
	templates.add('object/mobile/shared_dressed_selonian_f_02.iff')
	templates.add('object/mobile/shared_dressed_selonian_f_03.iff')
	templates.add('object/mobile/shared_dressed_selonian_f_04.iff')
	templates.add('object/mobile/shared_dressed_selonian_f_05.iff')
	templates.add('object/mobile/shared_dressed_selonian_f_06.iff')
	templates.add('object/mobile/shared_dressed_selonian_f_07.iff')
	templates.add('object/mobile/shared_dressed_selonian_f_08.iff')
	templates.add('object/mobile/shared_dressed_selonian_f_09.iff')
	templates.add('object/mobile/shared_dressed_selonian_f_10.iff')
	templates.add('object/mobile/shared_dressed_selonian_f_11.iff')
	templates.add('object/mobile/shared_dressed_selonian_f_12.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/carbine/shared_carbine_e11.iff', WeaponType.CARBINE, 1.0, 15, 'energy')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShot')
	mobileTemplate.setAttacks(attacks)
	
	lootPoolNames_1 = ['Junk']
	lootPoolChances_1 = [100]
	lootGroupChance_1 = 100
	mobileTemplate.addToLootGroups(lootPoolNames_1,lootPoolChances_1,lootGroupChance_1)
	
	core.spawnService.addMobileTemplate('selonian_healer', mobileTemplate)
	return