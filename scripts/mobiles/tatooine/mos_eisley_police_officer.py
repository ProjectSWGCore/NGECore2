import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('mission_mos_eisley_police_officer')
	mobileTemplate.setLevel(15)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup("Townsmanship")
	mobileTemplate.setAssistRange(0)
	mobileTemplate.setStalker(True)
	mobileTemplate.setOptionsBitmask(Options.ATTACKABLE)
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_eisley_officer_aqualish_female_01.iff')
	templates.add('object/mobile/shared_dressed_eisley_officer_aqualish_male_01.iff')
	templates.add('object/mobile/shared_dressed_eisley_officer_human_female_01.iff')
	templates.add('object/mobile/shared_dressed_eisley_officer_human_male_01.iff')
	templates.add('object/mobile/shared_dressed_eisley_officer_quarren_male_01.iff')
	templates.add('object/mobile/shared_dressed_eisley_officer_rodian_female_01.iff')
	templates.add('object/mobile/shared_dressed_eisley_officer_rodian_male_01.iff')
	templates.add('object/mobile/shared_dressed_eisley_officer_trandoshan_female_01.iff')
	templates.add('object/mobile/shared_dressed_eisley_officer_trandoshan_male_01.iff')
	templates.add('object/mobile/shared_dressed_eisley_officer_twilek_female_01.iff')
	templates.add('object/mobile/shared_dressed_eisley_officer_twilek_male_01.iff')
	templates.add('object/mobile/shared_dressed_eisley_officer_zabrak_female_01.iff')
	templates.add('object/mobile/shared_dressed_eisley_officer_zabrak_male_01.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/pistol/shared_pistol_scout_blaster.iff', WeaponType.PISTOL, 1.0, 15, 'energy')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)

	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShot')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('mission_mos_eisley_police_officer', mobileTemplate)
	return