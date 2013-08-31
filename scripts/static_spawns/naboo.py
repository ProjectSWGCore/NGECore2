import sys

def addPlanetSpawns(core, planet):

	stcSvc = core.staticService
	
	# Travel spawns
	
	stcSvc.spawnObject('object/tangible/travel/ticket_collector/shared_ticket_collector.iff', 'naboo', long(0), float(-5487.8), float(-149.4), float(-12.5), float(-0.710457), float(0.703739)) # Lake Retreat
	stcSvc.spawnObject('object/tangible/travel/ticket_collector/shared_ticket_collector.iff', 'naboo', long(0), float(4717.7), float(4.2), float(-4650.6), float(1), float(0)) # moenia starport
	stcSvc.spawnObject('object/tangible/travel/ticket_collector/shared_ticket_collector.iff', 'naboo', long(0), float(4968.6), float(4.4), float(-4883.3), float(0.71), float(-0.71)) # moenia shuttleport
	stcSvc.spawnObject('object/tangible/travel/ticket_collector/shared_ticket_collector.iff', 'naboo', long(0), float(5301.1), float(-192), float(6671.5), float(0), float(1)) # kaadara starport
	stcSvc.spawnObject('object/tangible/travel/ticket_collector/shared_ticket_collector.iff', 'naboo', long(0), float(5134.3), float(-191.4), float(6617.2), float(-0.373413), float(0.927665)) # kaadara shuttleport
	stcSvc.spawnObject('object/tangible/travel/ticket_collector/shared_ticket_collector.iff', 'naboo', long(0), float(1347.8), float(13), float(2760.3), float(0.988256), float(0.152808)) # keren starport
	stcSvc.spawnObject('object/tangible/travel/ticket_collector/shared_ticket_collector.iff', 'naboo', long(0), float(1558.3), float(25.6), float(2884.8), float(1), float(0)) # keren shuttleport a
	stcSvc.spawnObject('object/tangible/travel/ticket_collector/shared_ticket_collector.iff', 'naboo', long(0), float(2027.7), float(19.6), float(2535.2), float(-0.710457), float(0.703739)) # keren shuttleport b
	stcSvc.spawnObject('object/tangible/travel/ticket_collector/shared_ticket_collector.iff', 'naboo', long(0), float(5338.6), float(327.6), float(-1567.2), float(-0.710457), float(0.703739)) # deeja peek shuttleport


	
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'naboo', long(0), float(-5487), float(-149.4), float(-31.8), float(-0.710457), float(0.703739)) # Lake Retreat
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'naboo', long(0), float(5121.5), float(-191.4), float(6603.8), float(-0.373413), float(0.927665)) # kaadara shuttleport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'naboo', long(0), float(1577.6), float(25.6), float(2845.1), float(1), float(0)) # keren shuttleport a
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'naboo', long(0), float(2028.5), float(19.6), float(2515.6), float(-0.710457), float(0.703739)) # keren shuttleport b
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'naboo', long(0), float(5339.3), float(327.6), float(-1586.7), float(-0.710457), float(0.703739)) # deeja peek shuttleport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'naboo', long(0), float(4969.4), float(4.4), float(-4902.6), float(-0.710457), float(0.703739)) # moenia shuttleport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'naboo', long(1741539), float(11.5), float(0.6), float(51.3), float(0.195405), float(-0.980722)) # kaadara starport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'naboo', long(1741539), float(3.1), float(0.6), float(49), float(0), float(1)) # kaadara starport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'naboo', long(1741539), float(-2.7), float(0.6), float(49), float(0), float(1)) # kaadara starport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'naboo', long(1741539), float(-13.2), float(0.6), float(51.3), float(-0.195405), float(-0.980722)) # kaadara starport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'naboo', long(2125382), float(11.5), float(0.6), float(51.3), float(0.195405), float(-0.980722)) # keren starport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'naboo', long(2125382), float(3.1), float(0.6), float(49), float(0), float(1)) # keren starport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'naboo', long(2125382), float(-2.7), float(0.6), float(49), float(0), float(1)) # keren starport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'naboo', long(2125382), float(-13.2), float(0.6), float(51.3), float(-0.195405), float(-0.980722)) # keren starport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'naboo', long(4215410), float(11.5), float(0.6), float(51.3), float(0.195405), float(-0.980722)) # moenia starport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'naboo', long(4215410), float(3.1), float(0.6), float(49), float(0), float(1)) # moenia starport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'naboo', long(4215410), float(-2.7), float(0.6), float(49), float(0), float(1)) # moenia starport
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_travel.iff', 'naboo', long(4215410), float(-13.2), float(0.6), float(51.3), float(-0.195405), float(-0.980722)) # moenia starport

	
	

	return