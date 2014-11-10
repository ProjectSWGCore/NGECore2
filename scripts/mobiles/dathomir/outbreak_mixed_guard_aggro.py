import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('outbreak_mixed_guard_aggro')
	mobileTemplate.setLevel(90)
	mobileTemplate.setDifficulty(Difficulty.BOSS)
	
	templates = Vector()
	templates.add('object/mobile/shared_outbreak_imp_trooper_guard_m_01.iff')
	templates.add('object/mobile/shared_outbreak_imp_trooper_guard_m_02.iff')
	templates.add('object/mobile/shared_outbreak_imp_trooper_guard_m_03.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/rifle/shared_rifle_e11.iff', WeaponType.RIFLE, 1.0, 0, 'energy')
	weaponTemplates.add(weapontemplate)
	weapontemplate = WeaponTemplate('object/weapon/ranged/rifle/shared_rifle_t21_legendary.iff', WeaponType.RIFLE, 1.0, 0, 'energy')
	weaponTemplates.add(weapontemplate)
	weapontemplate = WeaponTemplate('object/weapon/ranged/carbine/shared_carbine_e11.iff', WeaponType.CARBINE, 1.0, 5, 'energy')
	weaponTemplates.add(weapontemplate)	
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedShot')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('outbreak_mixed_guard_aggro', mobileTemplate)

	return