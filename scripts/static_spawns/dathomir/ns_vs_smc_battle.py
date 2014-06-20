import sys
from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):
	stcSvc = core.staticService
	objSvc = core.objectService  
		
	Nightsister_Elder_1 = stcSvc.spawnObject('nightsister_elder', 'dathomir', long(0), float(-2520), float(130), float(1468), float(0.0007), float(0.999))
	Nightsister_Spellweaver_1 = stcSvc.spawnObject('nightsister_spell_weaver', 'dathomir', long(0), float(-2497), float(128), float(1484), float(0.0007), float(0.999))
	Nightsister_Spellweaver_2 = stcSvc.spawnObject('nightsister_spell_weaver', 'dathomir', long(0), float(-2539), float(128), float(1484), float(0.0007), float(0.999))
	Nightsister_Rancor_1 = stcSvc.spawnObject('nightsister_rancor', 'dathomir', long(0), float(-2480), float(126), float(1503), float(0.0007), float(0.999))
	Nightsister_RancorTamer_1 = stcSvc.spawnObject('nightsister_rancor_tamer', 'dathomir', long(0), float(-2495), float(122), float(1501), float(0.0007), float(0.999))
	Nightsister_Outcast_1 = stcSvc.spawnObject('nightsister_outcast', 'dathomir', long(0), float(-2480), float(119), float(1519), float(0.0007), float(0.999))
	Nightsister_Initiate_1 = stcSvc.spawnObject('nightsister_initiate', 'dathomir', long(0), float(-2494), float(124), float(1518), float(0.0007), float(0.999))
	Nightsister_Sentinel_1 = stcSvc.spawnObject('nightsister_sentinel', 'dathomir', long(0), float(-2516), float(124), float(1520), float(0.0007), float(0.999))
	Nightsister_Stalker_1 = stcSvc.spawnObject('nightsister_stalker', 'dathomir', long(0), float(-2516), float(128), float(1503), float(0.0007), float(0.999))
	Nightsister_Sentinel_2 = stcSvc.spawnObject('nightsister_sentinel', 'dathomir', long(0), float(-2516), float(124), float(1520), float(0.0007), float(0.999))
	Nightsister_Initiate_1 = stcSvc.spawnObject('nightsister_initiate', 'dathomir', long(0), float(-2540), float(125), float(1521), float(0.0007), float(0.999))
	Nightsister_Outcast_2 = stcSvc.spawnObject('nightsister_outcast', 'dathomir', long(0), float(-2557), float(125), float(1522), float(0.0007), float(0.999))
	Nightsister_Rancor_2 = stcSvc.spawnObject('nightsister_rancor', 'dathomir', long(0), float(-2556), float(125), float(1505), float(0.0007), float(0.999))
	Nightsister_RancorTamer_2 = stcSvc.spawnObject('nightsister_rancor_tamer', 'dathomir', long(0), float(-2540), float(126), float(1504), float(0.0007), float(0.999))
	
	SMC_Councilwoman_1 = stcSvc.spawnObject('singing_mtn_clan_councilwoman', 'dathomir', long(0), float(-2520), float(130), float(1598), float(0.999), float(0.002))
	SMC_Archwitch_1 = stcSvc.spawnObject('singing_mtn_clan_arch_witch', 'dathomir', long(0), float(-2543), float(131), float(1585), float(0.999), float(0.002))
	SMC_Archwitch_2 = stcSvc.spawnObject('singing_mtn_clan_arch_witch', 'dathomir', long(0), float(-2498), float(124), float(1585), float(0.999), float(0.002))
	SMC_Dragoon_1 = stcSvc.spawnObject('singing_mtn_clan_dragoon', 'dathomir', long(0), float(-2516), float(123), float(1565), float(0.999), float(0.002))
	SMC_RancorTamer_1 = stcSvc.spawnObject('singing_mtn_clan_rancor_tamer', 'dathomir', long(0), float(-2497), float(121), float(1568), float(0.999), float(0.002))
	SMC_Rancor_1 = stcSvc.spawnObject('singing_mountain_clan_rancor', 'dathomir', long(0), float(-2481), float(119), float(1568), float(0.999), float(0.002))
	SMC_RancorTamer_2 = stcSvc.spawnObject('singing_mtn_clan_rancor_tamer', 'dathomir', long(0), float(-2543), float(128), float(1566), float(0.999), float(0.002))
	SMC_Rancor_2 = stcSvc.spawnObject('singing_mountain_clan_rancor', 'dathomir', long(0), float(-2556), float(130), float(1564), float(0.999), float(0.002))
	SMC_Scout_1 = stcSvc.spawnObject('singing_mtn_clan_scout', 'dathomir', long(0), float(-2481), float(118), float(1547), float(0.999), float(0.002))
	SMC_Initiate_1 = stcSvc.spawnObject('singing_mtn_clan_initiate', 'dathomir', long(0), float(-2497), float(120), float(1547), float(0.999), float(0.002))
	SMC_Sentry_1 = stcSvc.spawnObject('singing_mountain_clan_sentry', 'dathomir', long(0), float(-2515), float(122), float(1547), float(0.999), float(0.002))
	SMC_Initiate_1 = stcSvc.spawnObject('singing_mtn_clan_initiate', 'dathomir', long(0), float(-2543), float(126), float(1547), float(0.999), float(0.002))
	SMC_Scout_1 = stcSvc.spawnObject('singing_mtn_clan_scout', 'dathomir', long(0), float(-2556), float(127), float(1547), float(0.999), float(0.002))
	return	
