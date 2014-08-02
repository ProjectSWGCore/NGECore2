import sys
from services.spawn import MobileTemplate
from services.spawn import WeaponTemplate
from resources.datatables import WeaponType
from resources.datatables import Difficulty
from resources.datatables import Options
from java.util import Vector

def addTemplate(core):
	mobileTemplate = MobileTemplate()
	
	mobileTemplate.setCreatureName('ancient_bull_rancor')
	mobileTemplate.setLevel(83)
	mobileTemplate.setDifficulty(Difficulty.BOSS)
	mobileTemplate.setMinSpawnDistance(4)
	mobileTemplate.setMaxSpawnDistance(8)
	mobileTemplate.setDeathblow(True)
	mobileTemplate.setScale(1.1)
	mobileTemplate.setMeatType("Carnivore Meat")
	mobileTemplate.setMeatAmount(1100)
	mobileTemplate.setHideType("Leathery Hide")
	mobileTemplate.setHideAmount(1000)
	mobileTemplate.setBoneType("Animal Bones")
	mobileTemplate.setBoneAmount(950)
	mobileTemplate.setSocialGroup("rancor")
	mobileTemplate.setAssistRange(12)
	mobileTemplate.setStalker(False)
	mobileTemplate.setOptionsBitmask(Options.AGGRESSIVE | Options.ATTACKABLE)
	
	templates = Vector()
	templates.add('object/mobile/shared_bull_rancor.iff')
	mobileTemplate.setTemplates(templates)
	
	
	attacks = Vector()
	attacks.add('bm_dampen_pain_5')
	attacks.add('bm_shaken_3')
	attacks.add('bm_stomp_5')
	mobileTemplate.setDefaultAttack('creatureMeleeAttack')
	mobileTemplate.setAttacks(attacks)
	
	core.spawnService.addMobileTemplate('ancient_bull_rancor', mobileTemplate)