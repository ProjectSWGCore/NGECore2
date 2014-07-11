from resources.common import OutOfBand
from resources.common import ProsePackage
from resources.objectives import DeliveryMissionObjective
import sys

def startConversation(core, actor, npc):

	player = actor.getPlayerObject()
	
	if player is None:
		return
	
	activeMissions = player.getActiveMissions()
	
	missionId = checkMissions(core, actor, npc, activeMissions)

	if missionId == 0:
		core.conversationService.sendStopConversation(actor, npc, 'mission/mission_generic', 'npc_job_request_no_job')
		return
	
	mission = core.objectService.getObject(missionId)
	
	if mission is None:
		return
	
	objective = mission.getObjective()

	if objective is None:
		core.conversationService.sendStopConversation(actor, npc, 'mission/mission_generic', 'npc_job_request_no_job')
		return

	if objective.getObjectivePhase() == 0:
		handlePickupStage(core, actor, npc, objective, mission)
		return
	
	elif objective.getObjectivePhase() == 1:
		handleDeliveryStage(core, actor, npc, objective, mission)
		return
	
	elif objective.getObjectivePhase() == 2:
		handleCompletionStage(core, actor, npc, objective, mission)
		return
	return

def endConversation(core, actor, npc):
	core.conversationService.sendStopConversation(actor, npc, 'mission/mission_generic', 'npc_job_request_no_job')
	return

def handlePickupStage(core, actor, npc, objective, mission):
	if objective.getMissionGiver() != npc.getObjectId():
		endConversation(core, actor, npc)
		return
	
	core.conversationService.sendStopConversation(actor, npc, mission.getTitle().getStfFilename(), 'm' + str(mission.getMissionId()) + 'p')
	objective.createDeliveryItem(core, actor)
	objective.update(core, actor)
	return

def handleDeliveryStage(core, actor, npc, objective, mission):
	
	if objective.getDropOffNpc() != npc.getObjectId():
		endConversation(core, actor, npc)
		return
	
	core.conversationService.sendStopConversation(actor, npc, mission.getTitle().getStfFilename(), 'm' + str(mission.getMissionId()) + 'r')
	core.objectService.destroyObject(objective.getDeliveryObject())
	objective.update(core, actor)
	return

def handleCompletionStage(core, actor, npc, objective, mission):
	
	if objective.getMissionGiver() != npc.getObjectId():
		endConversation(core, actor, npc)
		return
	
	core.conversationService.sendStopConversation(actor, npc, mission.getTitle().getStfFilename(), 'm' + str(mission.getMissionId()) + 's')
	core.missionService.handleMissionComplete(actor, mission)
	return

def checkMissions(core, actor, npc, activeMissions):
	for objId in activeMissions:
		missionObj = core.objectService.getObject(objId)

		if (missionObj != None):
			if (missionObj.getStartLocation() is not None and missionObj.getStartLocation().getObjectId() == npc.getObjectID()):
				return missionObj.getObjectID()
			elif (missionObj.getDestinationLocation() is not None and missionObj.getDestinationLocation().getObjectId() == npc.getObjectID()):
				return missionObj.getObjectID()
	return 0