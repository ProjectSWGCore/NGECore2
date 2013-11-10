import sys

def addPlanetSpawns(core, planet):

	stcSvc = core.staticService
	
	# Travel spawns
	
	stcSvc.spawnObject('object/tangible/travel/ticket_collector/shared_ticket_collector.iff', 'tatooine', long(0), float(-1090.8), float(12.6), float(-3554.9), float(-0.67), float(0.74)) # bestine shuttleport
	stcSvc.spawnObject('object/tangible/travel/ticket_collector/shared_ticket_collector.iff', 'tatooine', long(0), float(-1382.15), float(12), float(-3583.25), float(1), float(-0.08)) # bestine starport
	stcSvc.spawnObject('object/tangible/travel/ticket_collector/shared_ticket_collector.iff', 'tatooine', long(0), float(3427.5), float(5.6), float(-4644.1), float(-0.485545), float(0.874212)) # mos eisley shuttleport
	stcSvc.spawnObject('object/tangible/travel/ticket_collector/shared_ticket_collector.iff', 'tatooine', long(0), float(3622), float(5), float(-4788), float(-0.276903063059), float(0.960897982121)) # mos eisley starport
	
	stcSvc.spawnObject('object/creature/npc/theme_park/shared_player_transport.iff', 'tatooine', long(0), float(3618), float(5), float(-4801), float(0.500574469566), float(0.865693628788)) # mos eisley starport
	
	
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
	
	stcSvc.spawnObject('object/mobile/shared_lifeday_saun_dann.iff', 'tatooine', long(0), float(-5037.00), float(75), float(-6561), float(-0.75), float(0)) # Life Day
	stcSvc.spawnObject('object/tangible/holiday/life_day/shared_main_lifeday_tree.iff', 'tatooine', long(0), float(-5043.00), float(75), float(-6541.00), float(0.999132931232), float(-0.0416347384453)) # Lifeday Tree
	stcSvc.spawnObject('object/mobile/shared_lifeday_figrin_dan.iff', 'tatooine', long(0), float(-5046.00), float(75), float(-6560), float(-0.75), float(0)) # LD Figrin
	bandtat1 = stcSvc.spawnObject('object/mobile/shared_lifeday_figrin_dan_band.iff', 'tatooine', long(0), float(-5040.00), float(75), float(-6557), float(-0.75), float(0)) # LD Band 1
	bandtat1.setCustomName2('Doikk Nats')
	bandtat2 = stcSvc.spawnObject('object/mobile/shared_lifeday_figrin_dan_band.iff', 'tatooine', long(0), float(-5043.00), float(75), float(-6559), float(-0.75), float(0)) # LD band 2
	bandtat2.setCustomName2('Tech Mor')
	bandtat3 = stcSvc.spawnObject('object/mobile/shared_lifeday_figrin_dan_band.iff', 'tatooine', long(0), float(-5048.00), float(75), float(-6557), float(-0.75), float(0)) # LD Band 3
	bandtat3.setCustomName2('Nalan Cheel')
	bandtat4 = stcSvc.spawnObject('object/mobile/shared_lifeday_figrin_dan_band.iff', 'tatooine', long(0), float(-5045.00), float(75), float(-6555), float(-0.75), float(0)) # LD band 4
	bandtat4.setCustomName2('Sunil Eide')
	stcSvc.spawnObject('object/tangible/instrument/shared_nalargon.iff', 'tatooine', long(0), float(-5045.00), float(75), float(-6555), float(0.999132931232), float(-0.0416347384453)) # Drums
	stcSvc.spawnObject('object/tangible/instrument/shared_ommni_box.iff', 'tatooine', long(0), float(-5043.00), float(75), float(-6559), float(0.999132931232), float(-0.0416347384453)) # Box
	stcSvc.spawnObject('object/tangible/holiday/life_day/shared_life_day_tree.iff', 'tatooine', long(0), float(-5083.00), float(75), float(-6619.00), float(0.999132931232), float(-0.0416347384453)) # small tree

	return
	