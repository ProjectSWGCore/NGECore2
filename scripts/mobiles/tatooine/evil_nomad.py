import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector


def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('evil_nomad')
	mobileTemplate.setLevel(4)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)

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
	weapontemplate = WeaponTemplate('object/weapon/ranged/carbine/shared_carbine_dh17.iff', WeaponType.CARBINE, 1.0, 15, 'energy')
	weaponTemplates.add(weapontemplate)
	weapontemplate1 = WeaponTemplate('object/weapon/melee/polearm/shared_lance_electric_polearm.iff', WeaponType.POLEARMMELEE, 1.0, 6, 'kinetic')
	weaponTemplates.add(weapontemplate1)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShotpistol')
	mobileTemplate.setDefaultAttack('meleehit')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('evil_hermit', mobileTemplate)
	return