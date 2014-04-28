from resources.common import OutOfBand
from resources.common import ProsePackage
from resources.objectives import DeliveryMissionObjective
import sys

def startConversation(core, actor, npc):
	missionId = npc.getAttachment('assignedMission')
	
	if missionId is None:
		return
	
	mission = core.objectService.getObject(missionId)
	
	if mission is None:
		return
	
	objective = mission.getObjective()

	if mission.getGrandparent().getObjectId() != actor.getObjectId() or objective is None:
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
	
	if objective.getMissionGiver().getObjectId() != npc.getObjectId():
		endConversation(core, actor, npc)
		return
	
	core.conversationService.sendStopConversation(actor, npc, mission.getMissionDescription(), 'm' + str(mission.getMissionId()) + 'p')
	objective.createDeliveryItem(core, actor)
	objective.update(core, actor)
	return

def handleDeliveryStage(core, actor, npc, objective, mission):
	
	if objective.getDropOffNpc().getObjectId() != npc.getObjectId():
		endConversation(core, actor, npc)
		return
	
	core.conversationService.sendStopConversation(actor, npc, mission.getMissionDescription(), 'm' + str(mission.getMissionId()) + 'r')
	core.objectService.destroyObject(objective.getDeliveryObject())
	objective.update(core, actor)
	return

def handleCompletionStage(core, actor, npc, objective, mission):
	
	if objective.getMissionGiver().getObjectId() != npc.getObjectId():
		endConversation(core, actor, npc)
		return
	
	core.conversationService.sendStopConversation(actor, npc, mission.getMissionDescription(), 'm' + str(mission.getMissionId()) + 's')
	objective.complete(core, actor)
	return