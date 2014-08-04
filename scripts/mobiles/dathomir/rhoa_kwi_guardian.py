import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('rhoa_kwi_guardian')
	mobileTemplate.setLevel(64)
	mobileTemplate.setDifficulty(Difficulty.NORMAL)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setScale(1)
	mobileTemplate.setMeatType("Carnivore Meat")
	mobileTemplate.setMeatAmount(120)
	mobileTemplate.setHideType("Leathery Hide")
	mobileTemplate.setHideAmount(86)
	mobileTemplate.setBoneType("Animal Bones")
	mobileTemplate.setBoneAmount(76)
	mobileTemplate.setSocialGroup("kwi rhoa")
	mobileTemplate.setAssistRange(12)
	mobileTemplate.setStalker(True)
	mobileTemplate.setOptionsBitmask(Options.AGGRESSIVE | Options.ATTACKABLE)
	
	templates = Vector()
	templates.add('object/mobile/shared_kwi.iff')
	mobileTemplate.setTemplates(templates)
	
	attacks = Vector()
	attacks.add('bm_bite_4')
	attacks.add('bm_hamstring_4')
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('rhoa_kwi_guardian', mobileTemplate)