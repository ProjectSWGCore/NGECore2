import sys
from services.spawn import MobileTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
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
	mobileTemplate.setLevel(90)
	mobileTemplate.setDifficulty(2)
	mobileTemplate.setCustomWeaponTemplate('object/weapon/melee/2h_sword/crafted_saber/shared_sword_lightsaber_two_handed_gen5.iff')
	attacks = Vector()
	mobileTemplate.setDefaultAttack('saberHit')
	mobileTemplate.setAttacks(attacks)
	mobileTemplate.setCreatureName('eow_dark_jedi_sentinel_uber')
	mobileTemplate.setAttackRange(6)
	core.spawnService.addMobileTemplate('jedi_master', mobileTemplate)
	