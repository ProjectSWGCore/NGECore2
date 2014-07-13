import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('tatooine_sand_splitter_knave')
	mobileTemplate.setLevel(19)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup("sand splitter")
	mobileTemplate.setAssistRange(5)
	mobileTemplate.setStalker(False)
	mobileTemplate.setOptionsBitmask(Options.AGGRESSIVE | Options.ATTACKABLE)	
	
	
	templates = Vector()
	templates.add('object/mobile/shared_coa_aclo_elite_slicer_human_f.iff')
	templates.add('object/mobile/shared_coa_aclo_saboteur_human_m.iff')
	templates.add('object/mobile/shared_dressed_aakuan_defender_human_female_01.iff')
	templates.add('object/mobile/shared_dressed_aakuan_defender_human_male_01.iff')
	templates.add('object/mobile/shared_dressed_businessman_human_male_01.iff')
	templates.add('object/mobile/shared_dressed_combatmedic_trainer_human_female_01.iff')
	templates.add('object/mobile/shared_dressed_combatmedic_trainer_human_male_01.iff')
	templates.add('object/mobile/shared_dressed_corsec_detective_human_female_01.iff')
	templates.add('object/mobile/shared_dressed_corsec_detective_human_male_01.iff')
	templates.add('object/mobile/shared_dressed_criminal_smuggler_human_female_01.iff')
	templates.add('object/mobile/shared_dressed_criminal_smuggler_human_male_01.iff')
	templates.add('object/mobile/shared_zabrak_female.iff')
	templates.add('object/mobile/shared_zabrak_male.iff')
	
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/pistol/shared_pistol_d18.iff', WeaponType.PISTOL, 1.0, 24, 'energy')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShot')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('sand_splitter_knave', mobileTemplate)
	return