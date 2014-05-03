import sys
# Project SWG:   Jabba's Palace:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import StateStatus

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.
	
	reelo = stcSvc.spawnObject('object/mobile/shared_reelo_baruk.iff', 'tatooine', long(26560), float(-3.5), float(0.2), float(113.5), float(0), float(0))
	reelo.setCustomName2('Reelo Baruk')
	reelo.setOptionsBitmask(264)
	
	reeyees = stcSvc.spawnObject('object/mobile/shared_dressed_gran_thug_male_01.iff', 'tatooine', long(26560), float(5.8), float(0.2), float(115.7), float(0.70), float(-0.71))
	reeyees.setCustomName2('Ree-Yees')
	reeyees.setOptionsBitmask(264)
	
	ephant = stcSvc.spawnObject('object/mobile/shared_ephant_mon.iff', 'tatooine', long(26564), float(-6.1), float(5.8), float(86.1), float(0), float(0))
	ephant.setCustomName2('Ephant Mon')
	ephant.setOptionsBitmask(264)
	
	porcellus = stcSvc.spawnObject('object/mobile/shared_dressed_porcellus.iff', 'tatooine', long(26572), float(-44.0), float(3.0), float(63.4), float(0), float(0))
	porcellus.setCustomName2('Porcellus')
	porcellus.setOptionsBitmask(264)
	
	barada = stcSvc.spawnObject('object/mobile/shared_barada.iff', 'tatooine', long(26596), float(31.2), float(0.2), float(-1.0), float(0.98), float(-0.17))
	barada.setCustomName2('Barada')
	barada.setOptionsBitmask(264)

	bibMain = stcSvc.spawnObject('object/mobile/shared_bib_fortuna.iff', 'tatooine', long(26582), float(-11.1), float(2.0), float(49.5), float(0.70), float(0.71))
	bibMain.setCustomName2('Bib Fortuna')
	bibMain.setOptionsBitmask(264)
	
	jabba = stcSvc.spawnObject('object/mobile/shared_jabba_the_hutt.iff', 'tatooine', long(26582), float(-17.0), float(2.8), float(44.9), float(0.70), float(0.71))
	jabba.setCustomName2('Jabba the Hutt')
	jabba.setOptionsBitmask(264)
	
	oola = stcSvc.spawnObject('object/mobile/shared_oola.iff', 'tatooine', long(26582), float(-10.3), float(2.0), float(43.9), float(0.70), float(-0.71))
	oola.setCustomName2('Oola')
	oola.setOptionsBitmask(264)
	
	bobaFett = stcSvc.spawnObject('object/mobile/shared_boba_fett.iff', 'tatooine', long(26582), float(-1.0), float(3.0), float(33.1), float(0), float(0))
	bobaFett.setCustomName2('Boba Fett')
	bobaFett.setOptionsBitmask(264)
	
	ev9d9 = stcSvc.spawnObject('object/mobile/shared_ev_9d9.iff', 'tatooine', long(26574), float(18.8), float(0.2), float(78.7), float(1.0), float(0))
	ev9d9.setCustomName2('EV-9D9')
	ev9d9.setOptionsBitmask(264)
	
	malakili = stcSvc.spawnObject('object/mobile/shared_malakili.iff', 'tatooine', long(26599), float(17.3), float(-11.0), float(43.8), float(0), float(0))
	malakili.setCustomName2('Malakili')
	malakili.setOptionsBitmask(264)
	
# TODO Need to somehow get the cellID for the Rancor Pit or it will NEVER be spawned.
		
	maxRebo = stcSvc.spawnObject('object/mobile/shared_max_rebo.iff', 'tatooine', long(26582), float(-1.4), float(3.0), float(26.9), float(0.42), float(-0.9))
	maxRebo.setCustomName2('Max Rebo')
	maxRebo.setOptionsBitmask(264)
	
	droopy = stcSvc.spawnObject('object/mobile/shared_droopy_mccool.iff', 'tatooine', long(26582), float(-3.9), float(3.0), float(26.1), float(0.42), float(-0.9))
	droopy.setCustomName2('Droopy McCool')
	droopy.setOptionsBitmask(264)
	
	sySnootles = stcSvc.spawnObject('object/mobile/shared_sy_snootles.iff', 'tatooine', long(26582), float(-1.4), float(3.0), float(29.8), float(0.42), float(-0.))
	sySnootles.setCustomName2('Sy Snootles')
	sySnootles.setOptionsBitmask(264)
	
	g5po = stcSvc.spawnObject('object/mobile/shared_3po_protocol_droid_red.iff', 'tatooine', long(26582), float(-14.3), float(2.0), float(47.4), float(0.70), float(0.71))
	g5po.setCustomName2('G5-PO')
	g5po.setOptionsBitmask(264)
	return
	
