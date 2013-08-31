import sys

def addPlanetSpawns(core, planet):

	stcSvc = core.staticService
	
	# Travel spawns
	
	stcSvc.spawnObject('object/tangible/travel/ticket_collector/shared_ticket_collector.iff', 'tatooine', long(0), float(-1090.8), float(12.6), float(-3554.9), float(-0.67), float(0.74)) # bestine shuttleport
	stcSvc.spawnObject('object/tangible/travel/ticket_collector/shared_ticket_collector.iff', 'tatooine', long(0), float(-1382.15), float(12), float(-3583.25), float(1), float(-0.08)) # bestine starport
	stcSvc.spawnObject('object/tangible/travel/ticket_collector/shared_ticket_collector.iff', 'tatooine', long(0), float(3427.5), float(5.6), float(-4644.1), float(-0.485545), float(0.874212)) # mos eisley shuttleport
	stcSvc.spawnObject('object/tangible/travel/ticket_collector/shared_ticket_collector.iff', 'tatooine', long(0), float(3623.5), float(5), float(-4793.5), float(-0.103924), float(0.994585)) # mos eisley starport
	stcSvc.spawnObject('object/tangible/travel/ticket_collector/shared_ticket_collector.iff', 'tatooine', long(0), float(-2886.3), float(5.6), float(1929.4), float(-0.113814), float(0.993502)) # mos espa shuttleport a
	stcSvc.spawnObject('object/tangible/travel/ticket_collector/shared_ticket_collector.iff', 'tatooine', long(0), float(-3116.3), float(5.6), float(2166.1), float(-0.621933), float(0.783071)) # mos espa shuttleport b
	stcSvc.spawnObject('object/tangible/travel/ticket_collector/shared_ticket_collector.iff', 'tatooine', long(0), float(3623.5), float(5.6), float(2178.4), float(-0.123692), float(0.992321)) # mos espa shuttleport c
	stcSvc.spawnObject('object/tangible/travel/ticket_collector/shared_ticket_collector.iff', 'tatooine', long(0), float(38.15), float(52.6), float(-5532.2), float(1), float(0)) # anchorhead shuttleport
	stcSvc.spawnObject('object/tangible/travel/ticket_collector/shared_ticket_collector.iff', 'tatooine', long(0), float(1241.95), float(7), float(3053.42), float(0.89), float(0.46)) # mos entha starport
	stcSvc.spawnObject('object/tangible/travel/ticket_collector/shared_ticket_collector.iff', 'tatooine', long(0), float(1386.12), float(7.6), float(3473.81), float(1), float(0)) # mos entha shuttleport a
	stcSvc.spawnObject('object/tangible/travel/ticket_collector/shared_ticket_collector.iff', 'tatooine', long(0), float(1721.5), float(7.6), float(3191.3), float(1), float(0)) # mos entha shuttleport b
	stcSvc.spawnObject('object/tangible/travel/ticket_collector/shared_ticket_collector.iff', 'tatooine', long(0), float(-2820.5), float(5), float(2083.71), float(0), float(1)) # mos espa starport


	
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'tatooine', long(0), float(-1091.9), float(12.6), float(-3572.7), float(-0.645025), float(0.764162)) # bestine shuttleport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'tatooine', long(0), float(-2903.5), float(5.6), float(1924), float(-0.113814), float(0.993502)) # mos espa shuttleport a
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'tatooine', long(0), float(-3124.4), float(5.6), float(2138.1), float(0.621933), float(0.783071)) # mos espa shuttleport b
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'tatooine', long(0), float(-2810), float(12.6), float(2173.8), float(-0.123962), float(0.992321)) # mos espa shuttleport c
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'tatooine', long(0), float(55.7), float(52.6), float(-5531.4), float(1), float(0)) # anchorhead shuttleport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'tatooine', long(0), float(1403.6), float(7.6), float(3474.4), float(1), float(0)) # mos entha shuttleport a
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'tatooine', long(0), float(1739), float(7.6), float(3192.1), float(1), float(0)) # mos entha shuttleport b
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'tatooine', long(0), float(3418.4), float(5.6), float(-4659.3), float(-0.485545), float(0.874212)) # mos eisley shuttleport

	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'tatooine', long(1106372), float(11.5), float(0.6), float(51.3), float(0.195405), float(-0.980722)) # mos eisley starport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'tatooine', long(1106372), float(3.1), float(0.6), float(49), float(0), float(1)) # mos eisley starport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'tatooine', long(1106372), float(-2.7), float(0.6), float(49), float(0), float(1)) # mos eisley starport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'tatooine', long(1106372), float(-13.2), float(0.6), float(51.3), float(-0.195405), float(-0.980722)) # mos eisley starport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'tatooine', long(1026828), float(11.5), float(0.6), float(51.3), float(0.195405), float(-0.980722)) # bestine starport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'tatooine', long(1026828), float(3.1), float(0.6), float(49), float(0), float(1)) # bestine starport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'tatooine', long(1026828), float(-2.7), float(0.6), float(49), float(0), float(1)) # bestine starport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'tatooine', long(1026828), float(-13.2), float(0.6), float(51.3), float(-0.195405), float(-0.980722)) # bestine starport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'tatooine', long(1261655), float(11.5), float(0.6), float(51.3), float(0.195405), float(-0.980722)) # mos espa starport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'tatooine', long(1261655), float(3.1), float(0.6), float(49), float(0), float(1)) # mos espa starport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'tatooine', long(1261655), float(-2.7), float(0.6), float(49), float(0), float(1)) # mos espa starport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'tatooine', long(1261655), float(-13.2), float(0.6), float(51.3), float(-0.195405), float(-0.980722)) # mos espa starport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'tatooine', long(4005520), float(11.5), float(0.6), float(51.3), float(0.195405), float(-0.980722)) # mos entha starport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'tatooine', long(4005520), float(3.1), float(0.6), float(49), float(0), float(1)) # mos entha starport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'tatooine', long(4005520), float(-2.7), float(0.6), float(49), float(0), float(1)) # mos entha starport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'tatooine', long(4005520), float(-13.2), float(0.6), float(51.3), float(-0.195405), float(-0.980722)) # mos entha starport

	
	

	return