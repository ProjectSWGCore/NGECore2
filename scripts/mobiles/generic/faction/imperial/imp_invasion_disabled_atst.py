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
	
	mobileTemplate.setCustomName('A disabled AT-ST')
	mobileTemplate.setLevel(90)
	mobileTemplate.setDifficulty(Difficulty.ELITE)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup("imperial")
	mobileTemplate.setAssistRange(0)
	mobileTemplate.setStalker(False)
	mobileTemplate.setFaction("imperial")
	mobileTemplate.setFactionStatus(FactionStatus.Combatant)
	mobileTemplate.setOptionsBitmask(Options.ATTACKABLE)
	mobileTemplate.setAIEnabled(False)
	
	templates = Vector()
	templates.add('object/mobile/shared_atst.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector() # object/weapon/ranged/turret/shared_turret_heat.iff  object/weapon/ranged/vehicle/shared_vehicle_atst_ranged.iff
	weapontemplate = WeaponTemplate('object/weapon/ranged/vehicle/shared_vehicle_atst_ranged.iff', WeaponType.CARBINE, 2.0, 55, 'energy')
	#weapontemplate = WeaponTemplate('object/weapon/ranged/rifle/shared_rifle_t21.iff', WeaponType.RIFLE, 1.0, 24, 'energy')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('gcw_atst_attack_tower') #gcw_atst_attack_tower #rangedShot
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('imp_invasion_disabled_atst', mobileTemplate)
	return