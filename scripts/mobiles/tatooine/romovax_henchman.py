import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('tatooine_romo_vax_henchman')
	mobileTemplate.setLevel(20)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup("sennex")
	mobileTemplate.setAssistRange(10)
	mobileTemplate.setStalker(False)
	mobileTemplate.setOptionsBitmask(Options.AGGRESSIVE | Options.ATTACKABLE)
	
	templates = Vector()  #need to find correct template
	templates.add('object/mobile/shared_dressed_trader_thug_female_human_01.iff')
	templates.add('object/mobile/shared_dressed_trader_thug_female_human_02.iff')
	templates.add('object/mobile/shared_dressed_trader_thug_female_human_03.iff')
	templates.add('object/mobile/shared_dressed_trader_thug_female_twk_01.iff')
	templates.add('object/mobile/shared_dressed_trader_thug_female_twk_02.iff')
	templates.add('object/mobile/shared_dressed_trader_thug_male_human_02.iff')
	templates.add('object/mobile/shared_dressed_trader_thug_male_human_03.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/carbine/shared_carbine_e5.iff', WeaponType.CARBINE, 1.0, 15, 'energy')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShot')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('romovax_henchman', mobileTemplate)
	return