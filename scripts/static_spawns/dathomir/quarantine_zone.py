import sys
from resources.datatables import Options
from resources.datatables import State
from java.util import Vector
from engine.resources.scene import Point3D


def addPlanetSpawns(core, planet):
	stcSvc = core.staticService

	# Imperial Gate Guards - Entrance + General Starting Area
	guard1 = stcSvc.spawnObject('outbreak_imperial_stormtrooper_guards', 'dathomir', long(0), float(-5775), float(510), float(-6553), float(0.36), float(0.85))
	guard2 = stcSvc.spawnObject('outbreak_imperial_stormtrooper_guards', 'dathomir', long(0), float(-5785), float(510), float(-6545), float(0.43), float(0.85))
	guard3 = stcSvc.spawnObject('outbreak_imperial_stormtrooper_guards', 'dathomir', long(0), float(-5745), float(511), float(-6484), float(0.11), float(0.85))
	guard3 = stcSvc.spawnObject('outbreak_imperial_stormtrooper_guards', 'dathomir', long(0), float(-5744), float(511), float(-6480), float(0.37), float(0.85))
	liamwaurie1 = stcSvc.spawnObject('outbreak_imperial_officer_gate_keeper', 'dathomir', long(0), float(-5780), float(510), float(-6550), float(0.40), float(0.85))
	firmuspiett1 = stcSvc.spawnObject('outbreak_imperial_officer_captain_piett', 'dathomir', long(0), float(-5749), float(510), float(-6492), float(0.40), float(0.85))
	
	# Imperial Gate Troopers - Exit
	trooper1 = stcSvc.spawnObject('outbreak_imperial_stormtrooper_guards', 'dathomir', long(0), float(-5793), float(510), float(-6553), float(0.56), float(0.85))
	trooper2 = stcSvc.spawnObject('outbreak_imperial_stormtrooper_guards', 'dathomir', long(0), float(-5784), float(510), float(-6560), float(0.28), float(0.85))
	trooper3 = stcSvc.spawnObject('outbreak_imperial_stormtrooper_guards', 'dathomir', long(0), float(-5789), float(510), float(-6562), float(0.23), float(0.85))
	trooper4 = stcSvc.spawnObject('outbreak_imperial_stormtrooper_guards', 'dathomir', long(0), float(-5794), float(510), float(-6557), float(0.32), float(0.85))
	exittrooper1 = stcSvc.spawnObject('outbreak_facility_stormtrooper_survivor', 'dathomir', long(0), float(-5783), float(510), float(-6552), float(0.38), float(0.85))
	
	# Camp Alpha -5915, -6645
	alphaguard1 = stcSvc.spawnObject('outbreak_mixed_guard_aggro', 'dathomir', long(0), float(-5917), float(560), float(-6640), float(0.42), float(0.85))
	alphaguard2 = stcSvc.spawnObject('outbreak_mixed_guard_aggro', 'dathomir', long(0), float(-5908), float(560), float(-6645), float(0.34), float(0.85))
	alphaguard3 = stcSvc.spawnObject('outbreak_mixed_guard_aggro', 'dathomir', long(0), float(-5918), float(560), float(-6650), float(0.60), float(0.85))
	alphaguard4 = stcSvc.spawnObject('outbreak_mixed_guard_aggro', 'dathomir', long(0), float(-5913), float(560), float(-6670), float(-0.63), float(0.85))
	alphaguard3 = stcSvc.spawnObject('outbreak_mixed_guard_aggro', 'dathomir', long(0), float(-5930), float(560), float(-6690), float(0.26), float(0.85))
	#alphasurvivor1 = stcSvc.spawnObject('outbreak_camp_alpha_survivor', 'dathomir', long(0), float(-5930), float(560), float(-6690), float(0.26), float(0.85))
	
	# Camp Beta -6290 -7529
	betaguard1 = stcSvc.spawnObject('outbreak_mixed_guard_aggro', 'dathomir', long(0), float(-6300), float(560), float(-7535), float(-0.86), float(0.85))
	betaguard2 = stcSvc.spawnObject('outbreak_mixed_guard_aggro', 'dathomir', long(0), float(-6300), float(560), float(-7528), float(-0.88), float(0.85))
	betaguard3 = stcSvc.spawnObject('outbreak_mixed_guard_aggro', 'dathomir', long(0), float(-6290), float(560), float(-7520), float(1.38), float(0.85))
	betaguard4 = stcSvc.spawnObject('outbreak_mixed_guard_aggro', 'dathomir', long(0), float(-6280), float(560), float(-7520), float(-1.04), float(0.85))
	#beltasurvivor1 = stcSvc.spawnObject('outbreak_camp_beta_survivor', 'dathomir', long(0), float(-5930), float(560), float(-6690), float(0.26), float(0.85))
	
	# Camp Gamma -6815 -6454
	#gamasurvivor1 = stcSvc.spawnObject('outbreak_camp_gamma_survivor', 'dathomir', long(0), float(-5930), float(560), float(-6690), float(0.26), float(0.85))
	
	# Camp Delta -7142 -6941
	deltaguard1 = stcSvc.spawnObject('outbreak_mixed_guard_aggro', 'dathomir', long(0), float(-7155), float(560), float(-6955), float(-1.29), float(0.85))
	deltaguard2 = stcSvc.spawnObject('outbreak_mixed_guard_aggro', 'dathomir', long(0), float(-7135), float(560), float(-6955), float(1.10), float(0.85))
	deltaguard3 = stcSvc.spawnObject('outbreak_mixed_guard_aggro', 'dathomir', long(0), float(-7150), float(560), float(-6940), float(1.20), float(0.85))
	deltaguard4 = stcSvc.spawnObject('outbreak_mixed_guard_aggro', 'dathomir', long(0), float(-7143), float(560), float(-6910), float(1.78), float(0.85))
	deltaguard5 = stcSvc.spawnObject('outbreak_mixed_guard_aggro', 'dathomir', long(0), float(-7150), float(560), float(-6910), float(1.75), float(0.85))
	#beltasurvivor1 = stcSvc.spawnObject('outbreak_camp_delta_survivor', 'dathomir', long(0), float(-5930), float(560), float(-6690), float(0.26), float(0.85))
	
	# Camp Epsilon -7463 -7392
	hx138 = stcSvc.spawnObject('outbreak_stormtrooper_groupleader_hx138', 'dathomir', long(0), float(-7427), float(560), float(-7334), float(0.75), float(0.85))
	
	# Rancor Boss
	#rancorboss1 = stcSvc.spawnObject('outbreak_afflicted_rancor', 'dathomir', long(0), float(-7155), float(560), float(-6955), float(-1.29), float(0.85))
	
	# Nightsister Boss - TODO: Script Nightsister Zombie mobile
	#nightsisterboss1 = stcSvc.spawnObject('outbreak_afflicted_rancor', 'dathomir', long(0), float(-7155), float(560), float(-6955), float(-1.29), float(0.85))
	return	
