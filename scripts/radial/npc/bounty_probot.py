from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	radials.add(RadialOptions(0, 138, 1, '@mission/mission_generic:probe_droid_summon'))
	return
	
def handleSelection(core, owner, target, option):

	if option == 138 and target:
		id = owner.getAttachment('bountyMissionId')
		if id is None or id == 0:
			owner.sendSystemMessage('@mission/mission_generic:bounty_no_mission', 0)
			return
		
		mission = core.objectService.getObject(owner.getAttachment('bountyMissionId'))
		if mission is None:
			owner.sendSystemMessage('@mission/mission_generic:bounty_no_mission', 0)
			return

		if mission.getObjective().isArakydActive():
			owner.sendSystemMessage('@mission/mission_generic:probe_droid_too_many', 0)
			return

		if not core.terrainService.canBuildAtPosition(owner, owner.getPosition().x, owner.getPosition().z):
			owner.sendSystemMessage('@mission/mission_generic:probe_droid_bad_location', 0)
			return

		mission.getObjective().handleProbeDroidSummon(core, owner)
	return