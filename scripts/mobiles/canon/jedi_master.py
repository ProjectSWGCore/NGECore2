import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('eow_dark_jedi_sentinel_uber')
	mobileTemplate.setLevel(90)
	mobileTemplate.setDifficulty(Difficulty.BOSS)
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_dark_jedi_master_female_twk_01.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_master_female_twk_02.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_master_female_zab_01.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_master_female_zab_02.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_master_male_human_01.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_master_male_human_04.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_master_male_human_05.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_master_male_human_06.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_one_handed_gen5.iff', WeaponType.ONEHANDEDSABER, 1.0, 5, 'energy')
	weaponTemplates.add(weapontemplate)
	weapontemplate = WeaponTemplate('object/weapon/melee/2h_sword/crafted_saber/shared_sword_lightsaber_two_handed_gen5.iff', WeaponType.TWOHANDEDSABER, 1.0, 5, 'energy')
	weaponTemplates.add(weapontemplate)
	weapontemplate = WeaponTemplate('object/weapon/melee/polearm/crafted_saber/shared_sword_lightsaber_polearm_gen5.iff', WeaponType.POLEARMSABER, 1.0, 5, 'energy')
	weaponTemplates.add(weapontemplate)	
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('saberHit')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('jedi_master', mobileTemplate)

	return