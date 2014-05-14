import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('desert_demon_brawler')
	mobileTemplate.setLevel(13)
	mobileTemplate.setDifficulty(0)
	mobileTemplate.setAttackRange(12)
	mobileTemplate.setAttackSpeed(1.0)
	mobileTemplate.setWeaponType(1)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(False)
	mobileTemplate.setScale(1)
	mobileTemplate.setSocialGroup("desert demon")
	mobileTemplate.setAssistRange(4)
	mobileTemplate.setStalker(False)
	
	
	templates = Vector()
	templates.add('object/mobile/shared_dressed_tatooine_desert_demon_brawler.iff')
	mobileTemplate.setTemplates(templates)
	
	weaponTemplates = Vector()
	weapontemplate = WeaponTemplate('object/weapon/ranged/carbine/shared_carbine_cdef.iff', 1, 1.0)
	weaponTemplates.add(weapontemplate)
	weapontemplate1 = WeaponTemplate('object/weapon/ranged/pistol/shared_pistol_cdef.iff', 1, 1.0)
	weaponTemplates.add(weapontemplate1)
	mobileTemplate.setWeaponTemplateVector(weaponTemplates)
	
	attacks = Vector()
	mobileTemplate.setDefaultAttack('rangedshot')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('desert_demon_brawler', mobileTemplate)
	return