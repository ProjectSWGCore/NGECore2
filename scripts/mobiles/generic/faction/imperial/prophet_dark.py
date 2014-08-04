import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from resources.datatables import FactionStatus
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('prophet_of_the_dark_side')
	mobileTemplate.setLevel(80)
	mobileTemplate.setDifficulty(Difficulty.ELITE)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup("imperial")
	mobileTemplate.setAssistRange(6)
	mobileTemplate.setStalker(False)
	mobileTemplate.setFaction("imperial")
	mobileTemplate.setFactionStatus(FactionStatus.Combatant)
	
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_dark_jedi_elder_female_human_01.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_elder_female_human_02.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_elder_female_human_03.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_elder_male_human_01.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_elder_male_human_02.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_elder_male_human_03.iff')
	templates.add('object/mobile/shared_dressed_dark_jedi_elder_male_human_04.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_one_handed_gcw_s01_gen4.iff', WeaponType.ONEHANDEDSABER, 1.0, 6, 'energy')
	weapontemplate = WeaponTemplate('object/weapon/melee/sword/crafted_saber/shared_sword_lightsaber_one_handed_gcw_s01_gen5.iff', WeaponType.ONEHANDEDSABER, 1.0, 6, 'energy')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('saberhit')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('prophet_dark_side', mobileTemplate)
	return