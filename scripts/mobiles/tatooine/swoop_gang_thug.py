import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('tatooine_opening_smuggler_09')
	mobileTemplate.setLevel(8)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup("niko thug")
	mobileTemplate.setAssistRange(4)
	mobileTemplate.setStalker(False)
	mobileTemplate.setOptionsBitmask(Options.AGGRESSIVE | Options.ATTACKABLE)
		
	templates = Vector()
	templates.add('object/mobile/shared_dressed_criminal_swooper_human_female_01.iff')
	templates.add('object/mobile/shared_dressed_criminal_swooper_human_male_01.iff')
	templates.add('object/mobile/shared_dressed_criminal_swooper_zabrak_female_01.iff')
	templates.add('object/mobile/shared_dressed_criminal_swooper_zabrak_male_01.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()	
	weaponTemplate = WeaponTemplate('object/weapon/ranged/pistol/shared_pistol_cdef.iff', WeaponType.PISTOL, 1.0, 12, 'energy')
	weaponTemplates.add(weaponTemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShotpistol')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('swoop_gang_thug', mobileTemplate)

	return
