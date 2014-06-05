import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('sennex_slavemaster')
	mobileTemplate.setLevel(22)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(12)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(1)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup("sennex")
	mobileTemplate.setAssistRange(5)
	mobileTemplate.setStalker(False)
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_sennex_pirate_01.iff')
	templates.add('object/mobile/shared_dressed_sennex_pirate_02.iff')
	templates.add('object/mobile/shared_dressed_sennex_pirate_03.iff')
	templates.add('object/mobile/shared_dressed_sennex_pirate_04.iff')
	templates.add('object/mobile/shared_dressed_sennex_pirate_05.iff')
	templates.add('object/mobile/shared_dressed_sennex_pirate_06.iff')
	templates.add('object/mobile/shared_dressed_sennex_pirate_07.iff')
	templates.add('object/mobile/shared_dressed_sennex_pirate_08.iff')
	templates.add('object/mobile/shared_dressed_sennex_pirate_09.iff')
	templates.add('object/mobile/shared_dressed_sennex_pirate_10.iff')
	templates.add('object/mobile/shared_dressed_sennex_pirate_11.iff')
	templates.add('object/mobile/shared_dressed_sennex_pirate_12.iff')
	
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/carbine/shared_carbine_cdef.iff', 1, 1.0)
	weaponTemplates.add(weapontemplate)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShot')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('sennex_slavemaster', mobileTemplate)
	return