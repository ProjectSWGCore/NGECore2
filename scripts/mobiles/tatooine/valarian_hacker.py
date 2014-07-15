import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('tatooine_valarian_hacker')
	mobileTemplate.setLevel(19)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup("valarian_hacker")
	mobileTemplate.setAssistRange(4)
	mobileTemplate.setStalker(False)
	mobileTemplate.setOptionsBitmask(Options.AGGRESSIVE | Options.ATTACKABLE)
	
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_criminal_thug_aqualish_female_01.iff')
	templates.add('object/mobile/shared_dressed_criminal_thug_aqualish_female_02.iff')
	templates.add('object/mobile/shared_dressed_criminal_thug_aqualish_male_01.iff')
	templates.add('object/mobile/shared_dressed_criminal_thug_aqualish_male_02.iff')
	templates.add('object/mobile/shared_dressed_criminal_thug_bothan_female_01.iff')
	templates.add('object/mobile/shared_dressed_criminal_thug_bothan_male_01.iff')
	templates.add('object/mobile/shared_dressed_criminal_thug_human_female_01.iff')
	templates.add('object/mobile/shared_dressed_criminal_thug_human_female_02.iff')
	templates.add('object/mobile/shared_dressed_criminal_thug_human_male_01.iff')
	templates.add('object/mobile/shared_dressed_criminal_thug_human_male_02.iff')
	templates.add('object/mobile/shared_dressed_criminal_thug_rodian_female_01.iff')
	templates.add('object/mobile/shared_dressed_criminal_thug_rodian_male_01.iff')
	templates.add('object/mobile/shared_dressed_criminal_thug_trandoshan_female_01.iff')
	templates.add('object/mobile/shared_dressed_criminal_thug_trandoshan_male_01.iff')
	templates.add('object/mobile/shared_dressed_criminal_thug_zabrak_female_01.iff')
	templates.add('object/mobile/shared_dressed_criminal_thug_zabrak_male_01.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/pistol/shared_pistol_cdef.iff', WeaponType.PISTOL, 1.0, 15, 'energy')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShotpistol')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('valarian_hacker', mobileTemplate)
	return