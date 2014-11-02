# Project SWG:
# (C)2014 ProjectSWG

import sys
from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):

	stcSvc = core.staticService
	objSvc = core.objectService
	
	# New Player spawn spot
	#stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'umbra', long(0), float(153), float(25), float(160), float(0), float(1))
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_npe_transition.iff', 'umbra', long(0), float(151), float(25), float(160), float(1), float(0))
	
	# Corellia
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'corellia', long(0), float(-166), float(28), float(-4747), float(0.70), float(0.71))
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'corellia', long(0), float(-5026.85302734), float(21), float(-2311.02832031), float(0.70), float(0.72))
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'corellia', long(0), float(6671), float(330), float(-5932), float(0.70), float(0.71))
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'corellia', long(0), float(-3733), float(86), float(3222), float(0.70), float(0.71))
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'corellia', long(0), float(3347), float(308), float(5524), float(0.70), float(0.71))
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'corellia', long(0), float(-5536), float(15.8184862137), float(-6059), float(0.70), float(0.71))
	
	# Dantooine
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'dantooine', long(0), float(-600), float(3), float(2490.29711914), float(0.70), float(0.71))
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'dantooine', long(0), float(-4203), float(3), float(-2349), float(0.70), float(0.71))
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'dantooine', long(0), float(1546), float(4), float(-6366.95507812), float(0), float(1))
		
	# Dathomir
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'dathomir', long(0), float(-28.8136539459), float(18), float(-1588.14794922), float(0.70), float(0.71))
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'dathomir', long(0), float(637), float(6), float(3087), float(0.70), float(0.71))
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'dathomir', long(0), float(-5674), float(511.885223389), float(-6472), float(0.70), float(0.71))
	
	# Endor
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'endor', long(0), float(-931), float(76), float(1551), float(0.70), float(0.71))
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'endor', long(0), float(3204.09521484), float(24), float(-3450.5456543), float(0), float(1))
	
	# Lok
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'lok', long(0), float(489), float(9), float(5489), float(0.70), float(0.71))
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'lok', long(0), float(-1822.56750488), float(12), float(-3098.45849609), float(1), float(0))
	
	# Naboo
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'naboo', long(0), float(5212), float(-192), float(6685), float(0.70), float(0.71))
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'naboo', long(0), float(5303.63916016), float(326), float(-1587.58911133), float(0.774), float(0.633))
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'naboo', long(0), float(4946.7109375), float(3.73088502884), float(-4891.52490234), float(0.718), float(0.695))
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'naboo', long(0), float(-5465), float(-150), float(-3), float(0.70), float(0.71))
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'naboo', long(0), float(1513.641), float(25), float(2758.568), float(0.757), float(-0.653))
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'naboo', long(0), float(-4912.171), float(6), float(4096.535), float(0.93), float(0.367))
	
	# Rori
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'rori', long(0), float(-5272), float(80), float(-2234), float(0.70), float(0.71))
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'rori', long(0), float(3702), float(96), float(-6404), float(0.70), float(0.71))
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'rori', long(0), float(5315), float(80), float(6187), float(0.70), float(0.71))
	
	# Talus
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'talus', long(0), float(391), float(6), float(-2930), float(0.70), float(0.71))
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'talus', long(0), float(4505.03), float(2), float(5252.344), float(0.87), float(-0.498))
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'talus', long(0), float(-2159.83), float(20), float(2344.023), float(0.024), float(1))
		
	# Tatooine
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'tatooine', long(0), float(-1271), float(12), float(-3590), float(0.70), float(0.71))
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'tatooine', long(0), float(3501.58), float(5), float(-4812.436), float(0.94), float(0.35))
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'tatooine', long(0), float(118), float(52), float(-5358), float(0.70), float(0.71))
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'tatooine', long(0), float(1326), float(7), float(3144), float(0.70), float(0.71))
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'tatooine', long(0), float(-5036.037), float(75), float(-6620.237), float(0.825), float(-0.564))
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'tatooine', long(0), float(-2889), float(5), float(2143), float(0.70), float(0.71))
	
	# Yavin IV
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'yavin4', long(0), float(4070), float(37), float(-6211), float(0.70), float(0.71))
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'yavin4', long(0), float(-6926.225), float(73), float(-5743.343), float(1), float(0.03))
	stcSvc.spawnObject('object/tangible/terminal/shared_terminal_character_builder.iff', 'yavin4', long(0), float(-250.775), float(35), float(4888.408), float(0.814), float(-0.58))
	
	return
	
