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
	
	mobileTemplate.setCreatureName('gcw_city_rebel_general')
	mobileTemplate.setLevel(110)
	mobileTemplate.setHealth(1000000)
	mobileTemplate.setDifficulty(Difficulty.BOSS)

	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setSocialGroup("rebel")
	mobileTemplate.setAssistRange(0)
	mobileTemplate.setStalker(False)
	mobileTemplate.setFaction("rebel")
	mobileTemplate.setFactionStatus(FactionStatus.Combatant)
	mobileTemplate.setOptionsBitmask(Options.INVULNERABLE)
	mobileTemplate.setAIEnabled(False)
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_rebel_general_human_female_01.iff')
	templates.add('object/mobile/shared_dressed_rebel_general_human_female_02.iff')
	templates.add('object/mobile/shared_dressed_rebel_general_moncal_male_01.iff')
	templates.add('object/mobile/shared_dressed_rebel_general_old_twilek_male_01.iff')
	templates.add('object/mobile/shared_dressed_rebel_general_rieekan_01.iff')
	templates.add('object/mobile/shared_dressed_rebel_general_rodian_female_01.iff')				
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/carbine/shared_carbine_e5.iff', WeaponType.CARBINE, 1.0, 15, 'energy')
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShot')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('rebel_invasion_general', mobileTemplate)
	return