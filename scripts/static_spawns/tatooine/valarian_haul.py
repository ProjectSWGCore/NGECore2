import sys
from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):

	stcSvc = core.staticService
	
	#inside depot
	#Gate
	stcSvc.spawnObject('valarian_depot_guard', 'tatooine', long(0), float(-1640), float(9.9), float(-3272), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_depot_guard', 'tatooine', long(0), float(-1655), float(9.9), float(-3279), float(0), float(0), float(0), float(0), 45)
	#inner Place	
	stcSvc.spawnObject('valarian_depot_worker', 'tatooine', long(0), float(-1666), float(9.9), float(-3287), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_depot_swooper', 'tatooine', long(0), float(-1634), float(9.9), float(-3300), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_depot_scout', 'tatooine', long(0), float(-1640), float(9.9), float(-3298), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('valarian_depot_boss', 'tatooine', long(0), float(-1618), float(9.2), float(-3327), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_depot_swooper', 'tatooine', long(0), float(-1627), float(8.3), float(-3335), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_depot_foreman', 'tatooine', long(0), float(-1661), float(7.8), float(-3331), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_depot_worker', 'tatooine', long(0), float(-1703.5), float(12.3), float(-3330), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_depot_swooper', 'tatooine', long(0), float(-1706), float(12.8), float(-3333), float(0), float(0), float(0), float(0), 45)

	#down the hill
	stcSvc.spawnObject('valarian_thief', 'tatooine', long(0), float(-1728), float(18.9), float(-3347), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_scout', 'tatooine', long(0), float(-1730), float(19.9), float(-3347), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_enforcer', 'tatooine', long(0), float(-1730), float(19), float(-3351), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_scout', 'tatooine', long(0), float(-1733), float(20.4), float(-3328), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_thief', 'tatooine', long(0), float(-1734), float(24.8), float(-3249), float(0), float(0), float(0), float(0), 45)
	
	stcSvc.spawnObject('valarian_thief', 'tatooine', long(0), float(-1728), float(18.9), float(-3347), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_scout', 'tatooine', long(0), float(-1730), float(19.9), float(-3347), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_enforcer', 'tatooine', long(0), float(-1730), float(19), float(-3351), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_thief', 'tatooine', long(0), float(-1733), float(20.4), float(-3328), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_thug', 'tatooine', long(0), float(-1734), float(24.8), float(-3249), float(0), float(0), float(0), float(0), 45)
	
	#around camp
	stcSvc.spawnObject('valarian_thief', 'tatooine', long(0), float(-1676), float(11.2), float(-3260), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_scout', 'tatooine', long(0), float(-1684), float(12.8), float(-3260), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_thug', 'tatooine', long(0), float(-1695), float(14.7), float(-3274), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_scout', 'tatooine', long(0), float(-1699), float(16.4), float(-3271), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_thief', 'tatooine', long(0), float(-1708), float(20.7), float(-3256), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('valarian_scout', 'tatooine', long(0), float(-1710), float(21.9), float(-3249), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_enforcer', 'tatooine', long(0), float(-1621), float(11.3), float(-3248), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_thug', 'tatooine', long(0), float(-1619), float(11.4), float(-3247.5), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_enforcer', 'tatooine', long(0), float(-1588), float(7.8), float(-3306), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_thief', 'tatooine', long(0), float(-1575), float(7.9), float(-3312), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_thief', 'tatooine', long(0), float(-1571), float(8.1), float(-3309), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_thief', 'tatooine', long(0), float(-1585), float(7.6), float(-3318), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_thief', 'tatooine', long(0), float(-1590), float(7.8), float(-3325), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('valarian_thug', 'tatooine', long(0), float(-1673), float(8.7), float(-3379), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_enforcer', 'tatooine', long(0), float(-1686), float(9.7), float(-3376), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_thief', 'tatooine', long(0), float(-1681), float(8.7), float(-3367), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_thief', 'tatooine', long(0), float(-1650), float(8.7), float(-3360), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_thief', 'tatooine', long(0), float(-1642), float(8.7), float(-3367), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_thief', 'tatooine', long(0), float(-1638), float(8.7), float(-3369), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('valarian_scout', 'tatooine', long(0), float(-1640), float(8.7), float(-3372), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_enforcer', 'tatooine', long(0), float(-1647), float(8.7), float(-3377), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_thug', 'tatooine', long(0), float(-1592), float(7.4), float(-3362), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_thief', 'tatooine', long(0), float(-1603), float(7.4), float(-3363), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('valarian_scout', 'tatooine', long(0), float(-1608), float(7.4), float(-3367), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_enforcer', 'tatooine', long(0), float(-1610), float(7.4), float(-3366), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_thug', 'tatooine', long(0), float(-1608), float(7.4), float(-3370), float(0), float(0), float(0), float(0), 45)
	#up the hill
	stcSvc.spawnObject('valarian_shuttle_guard', 'tatooine', long(0), float(-1754), float(31.5), float(-3325), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_shuttle_guard', 'tatooine', long(0), float(-1754), float(31.5), float(-3328), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_shuttle_guard', 'tatooine', long(0), float(-1759), float(31.5), float(-3320), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_shuttle_guard', 'tatooine', long(0), float(-1762), float(31.5), float(-3327), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_thief', 'tatooine', long(0), float(-1730), float(23.9), float(-3324), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_scout', 'tatooine', long(0), float(-1731), float(24.2), float(-3315), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_enforcer', 'tatooine', long(0), float(-1733), float(25.9), float(-3312), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_depot_foreman', 'tatooine', long(0), float(-1739), float(28.6), float(-3307), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_scout', 'tatooine', long(0), float(-1726), float(22.1), float(-3297), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_thief', 'tatooine', long(0), float(-1744), float(28.2), float(-3290), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_henchman', 'tatooine', long(0), float(-1812), float(31.5), float(-3293), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_enforcer', 'tatooine', long(0), float(-1814), float(31.5), float(-3289), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('valarian_thug', 'tatooine', long(0), float(-1845), float(29.9), float(-3326), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_thief', 'tatooine', long(0), float(-1840), float(31.5), float(-3332), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_scout', 'tatooine', long(0), float(-1825), float(31.5), float(-3334), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_thief', 'tatooine', long(0), float(-1819), float(31.5), float(-3374), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_scout', 'tatooine', long(0), float(-1814), float(31.4), float(-3374), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_henchman', 'tatooine', long(0), float(-1810), float(31.5), float(-3365), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_henchman', 'tatooine', long(0), float(-1786), float(30.3), float(-3389), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_scout', 'tatooine', long(0), float(-1775), float(30.1), float(-3386), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_thief', 'tatooine', long(0), float(-1770), float(31.4), float(-3377), float(0), float(0), float(0), float(0), 45)
	return
	
