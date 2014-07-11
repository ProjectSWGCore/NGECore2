import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('watto_thieves')
	mobileTemplate.setLevel(16)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup("junk thieves")
	mobileTemplate.setAssistRange(0)
	mobileTemplate.setStalker(False)
	mobileTemplate.setOptionsBitmask(Options.AGGRESSIVE | Options.ATTACKABLE)	
	
	templates = Vector()
	templates.add('object/mobile/shared_human_female.iff')
	templates.add('object/mobile/shared_human_male.iff')
	templates.add('object/mobile/shared_zabrak_female.iff')
	templates.add('object/mobile/shared_zabrak_male.iff')
	templates.add('object/mobile/shared_rodian_female.iff')
	templates.add('object/mobile/shared_rodian_male.iff')
	templates.add('object/mobile/shared_moncal_female.iff')
	templates.add('object/mobile/shared_moncal_male.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/pistol/shared_pistol_cdef.iff', WeaponType.PISTOL, 1.0, 15, 'energy')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('meleehit')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('slum_thief', mobileTemplate)
	return