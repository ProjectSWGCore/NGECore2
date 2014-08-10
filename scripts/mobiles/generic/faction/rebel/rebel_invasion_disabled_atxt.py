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
	
	mobileTemplate.setCustomName('A disabled AT-XT')
	mobileTemplate.setLevel(90)
	mobileTemplate.setDifficulty(Difficulty.ELITE)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup("rebel")
	mobileTemplate.setAssistRange(0)
	mobileTemplate.setStalker(False)
	mobileTemplate.setFaction("rebel")
	mobileTemplate.setFactionStatus(FactionStatus.Combatant)
	mobileTemplate.setOptionsBitmask(Options.ATTACKABLE)
	mobileTemplate.setAIEnabled(False)
	
	templates = Vector()
	templates.add('object/mobile/shared_atxt.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/vehicle/shared_vehicle_atst_ranged.iff', WeaponType.CARBINE, 1.0, 15, 'energy')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('gcw_atst_attack_tower')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('rebel_invasion_disabled_atxt', mobileTemplate)
	return