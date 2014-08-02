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
	
	mobileTemplate.setCreatureName('crackdown_stormtrooper_hard')
	mobileTemplate.setLevel(50)
	mobileTemplate.setDifficulty(Difficulty.ELITE)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup("imperial")
	mobileTemplate.setAssistRange(6)
	mobileTemplate.setStalker(False)
	mobileTemplate.setFaction("imperial")
	mobileTemplate.setFactionStatus(FactionStatus.Combatant)
	
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_stormtrooper_black_black.iff')
	templates.add('object/mobile/shared_dressed_stormtrooper_black_blue.iff')
	templates.add('object/mobile/shared_dressed_stormtrooper_black_gold.iff')
	templates.add('object/mobile/shared_dressed_stormtrooper_black_green.iff')
	templates.add('object/mobile/shared_dressed_stormtrooper_black_grey.iff')
	templates.add('object/mobile/shared_dressed_stormtrooper_black_red.iff')
	templates.add('object/mobile/shared_dressed_stormtrooper_black_white.iff')
	templates.add('object/mobile/shared_dressed_stormtrooper_white_black.iff')
	templates.add('object/mobile/shared_dressed_stormtrooper_white_blue.iff')
	templates.add('object/mobile/shared_dressed_stormtrooper_white_gold.iff')
	templates.add('object/mobile/shared_dressed_stormtrooper_white_green.iff')
	templates.add('object/mobile/shared_dressed_stormtrooper_white_grey.iff')
	templates.add('object/mobile/shared_dressed_stormtrooper_white_red.iff')
	templates.add('object/mobile/shared_dressed_stormtrooper_white_white.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/carbine/shared_carbine_e11.iff', WeaponType.CARBINE, 1.0, 15, 'energy')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShot')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('elite_imperial_stormrifle_50', mobileTemplate)
	return