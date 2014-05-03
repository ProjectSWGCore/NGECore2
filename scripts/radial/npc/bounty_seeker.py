from resources.common import RadialOptions
from resources.common import SpawnPoint
import time
import sys

def createRadial(core, owner, target, radials):
	radials.clear()
	radials.add(RadialOptions(0, 138, 0, 'Track Target'))
	radials.add(RadialOptions(1, 136, 0, '@mission/mission_generic:probe_droid_track_target')) # Track Bounty Target
	radials.add(RadialOptions(1, 137, 0, '@mission/mission_generic:probe_droid_find_target')) # Identify Bounty Target
	return
	
def handleSelection(core, owner, target, option):

	if option == 136 and target or option == 138 and target:
		id = owner.getAttachment('bountyMissionId')
		if id is None or id == 0:
			owner.sendSystemMessage('@mission/mission_generic:bounty_no_mission', 0)
			return
		
		mission = core.objectService.getObject(owner.getAttachment('bountyMissionId'))
		if mission is None:
			owner.sendSystemMessage('@mission/mission_generic:bounty_no_mission', 0)
			return

		if mission.getObjective().isSeekerActive():
			owner.sendSystemMessage('@mission/mission_generic:bounty_already_tracking', 0)
			return
		pos = SpawnPoint.getRandomPosition(owner.getPosition(), 1, 3, owner.getPlanetId())
		ori = owner.getOrientation()
		seeker = core.staticService.spawnObject('object/creature/npc/droid/crafted/shared_probe_droid_advanced.iff', owner.getPlanet().getName(), 0, pos.x, pos.y, pos.z, ori.y, ori.w)
		time.sleep(3)
		owner.sendSystemMessage('@mission/mission_generic:seeker_droid_standby', 0)
		seeker.setPosture(8)
		time.sleep(7.5)
		mission.getObjective().beginSeekerUpdates(core, owner)
		return
	
	if option == 137 and target:
		return
	return