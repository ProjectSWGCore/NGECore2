import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('dark_jedi_knight')
	mobileTemplate.setLevel(90)
	mobileTemplate.setMinLevel(62)
	mobileTemplate.setMaxLevel(70)
	mobileTemplate.setDifficulty(1)
	mobileTemplate.setAttackRange(6)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(9)
	mobileTemplate.setMinSpawnDistance(3)
	mobileTemplate.setMaxSpawnDistance(5)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setSocialGroup('dark jedi')
	mobileTemplate.setAssistRange(12)
	mobileTemplate.setRespawnTime(300)
	mobileTemplate.setOptionsBitmask(192)
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_dark_jedi_male_human_01.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_male_human_02.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_male_human_03.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_male_human_04.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_male_human_05.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_male_human_06.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_male_twk_01.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_male_twk_02.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_male_twk_03.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_male_zab_01.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_male_zab_02.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_male_zab_03.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_female_human_01.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_female_human_02.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_female_human_03.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_female_human_04.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_female_twk_01.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_female_twk_02.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_female_twk_03.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_female_zab_01.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_female_zab_02.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_female_zab_03.iff')
	
	
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_one_handed_gen5.iff', 9, 1.0)
	weaponTemplates.add(weapontemplate)
	weapontemplate = WeaponTemplate('object/weapon/melee/2h_sword/crafted_saber/shared_sword_lightsaber_two_handed_gen5.iff', 10, 1.0)
	weaponTemplates.add(weapontemplate)
	weapontemplate = WeaponTemplate('object/weapon/melee/polearm/crafted_saber/shared_sword_lightsaber_polearm_gen5.iff', 11, 1.0)
	weaponTemplates.add(weapontemplate)	
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('saberHit')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('dark_jedi_knight', mobileTemplate)
	