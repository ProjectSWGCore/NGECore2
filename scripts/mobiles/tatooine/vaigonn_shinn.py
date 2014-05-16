import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('tatooine_vaigon_shinn')
	mobileTemplate.setLevel(8)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(12)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(1)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup("shinn mugger")
	mobileTemplate.setAssistRange(4)
	mobileTemplate.setStalker(False)
	mobileTemplate.setOptionBitmask(192)
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_commoner_tatooine_nikto_male_01.iff')
	templates.add('object/mobile/shared_dressed_commoner_tatooine_nikto_male_02.iff')
	templates.add('object/mobile/shared_dressed_commoner_tatooine_nikto_male_03.iff')
	templates.add('object/mobile/shared_dressed_commoner_tatooine_nikto_male_04.iff')
	templates.add('object/mobile/shared_dressed_commoner_tatooine_nikto_male_05.iff')
	templates.add('object/mobile/shared_dressed_commoner_tatooine_nikto_male_06.iff')
	templates.add('object/mobile/shared_dressed_commoner_tatooine_nikto_male_07.iff')
	templates.add('object/mobile/shared_dressed_commoner_tatooine_nikto_male_08.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/carbine/shared_carbine_cdef.iff', 1, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedshot')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('vaigon_shinn', mobileTemplate)
	return