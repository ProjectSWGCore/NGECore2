import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('evil_nomad')
	mobileTemplate.setLevel(4)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(12)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(1)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup("evil")
	mobileTemplate.setAssistRange(4)
	mobileTemplate.setStalker(True)
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_tatooine_nomad.iff')
	mobileTemplate.setTemplates(templates)

	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/carbine/shared_carbine_dh17.iff', 1, 1.0)
	weaponTemplates.add(weapontemplate)
	weapontemplate1 = WeaponTemplate('object/weapon/melee/polearm/shared_lance_electric_polearm.iff', 7, 1.0)
	weaponTemplates.add(weapontemplate1)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedshotpistol')
	mobileTemplate.setDefaultAttack('meleehit')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('evil_hermit', mobileTemplate)
	return