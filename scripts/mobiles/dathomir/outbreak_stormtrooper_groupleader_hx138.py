import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('outbreak_stormtrooper_groupleader_hx138')
	mobileTemplate.setLevel(90)
	mobileTemplate.setDifficulty(Difficulty.BOSS)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setOptionsBitmask(Options.CONVERSABLE)
	mobileTemplate.setConversationFileName('outbreak_imperial_trooper_gate_keeper') #Needs correct conversation when made
	
	templates = Vector()
	templates.add('object/mobile/shared_stormtrooper.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/rifle/shared_rifle_e11.iff', WeaponType.RIFLE, 1.0, 0, 'kinetic')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedHit')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('outbreak_stormtrooper_groupleader_hx138', mobileTemplate)
	return