import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('sennex_lookout')
	mobileTemplate.setLevel(18)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup("sennex")
	mobileTemplate.setAssistRange(5)
	mobileTemplate.setStalker(False)
	mobileTemplate.setOptionsBitmask(Options.AGGRESSIVE | Options.ATTACKABLE)	
	
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_sennex_pirate_01.iff')
	templates.add('object/mobile/shared_dressed_sennex_pirate_02.iff')
	templates.add('object/mobile/shared_dressed_sennex_pirate_03.iff')
	templates.add('object/mobile/shared_dressed_sennex_pirate_04.iff')
	templates.add('object/mobile/shared_dressed_sennex_pirate_05.iff')
	templates.add('object/mobile/shared_dressed_sennex_pirate_06.iff')
	templates.add('object/mobile/shared_dressed_sennex_pirate_07.iff')
	templates.add('object/mobile/shared_dressed_sennex_pirate_08.iff')
	templates.add('object/mobile/shared_dressed_sennex_pirate_09.iff')
	templates.add('object/mobile/shared_dressed_sennex_pirate_10.iff')
	templates.add('object/mobile/shared_dressed_sennex_pirate_11.iff')
	templates.add('object/mobile/shared_dressed_sennex_pirate_12.iff')
	
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/carbine/shared_carbine_cdef.iff', WeaponType.CARBINE, 1.0, 15, 'energy')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShot')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('sennex_lookout', mobileTemplate)
	return