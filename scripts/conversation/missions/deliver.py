from resources.common import OutOfBand
from resources.common import ProsePackage
from resources.objectives import DeliveryMissionObjective
import sys

def startConversation(core, actor, npc):
	mission = npc.getAttachment('assignedMission')
	
	if mission is None:
		return
	
	objective = mission.getObjective()

	if mission.getGrandparent().getObjectId() != actor.getObjectId() or objective is None:
		core.conversationService.sendStopConversation(actor, npc, 'conversation/respecseller', 's_38')
		return

	if objective.getObjectivePhase() == 0:
		handleFirstDeliveryStage(core, actor, npc, objective, mission)
		return
	
	#convSvc.sendConversationMessage(actor, npc, OutOfBand(ProsePackage(mission.getMissionDescription(), 'm' + str(mission.getMissionId()) + 'p')))
	return

def endConversation(core, actor, npc):
	return

def handleFirstDeliveryStage(core, actor, npc, objective, mission):
	core.conversationService.sendStopConversation(actor, npc, mission.getMissionDescription(), 'm' + str(mission.getMissionId()) + 'p')
	objective.createDeliveryItem()
	objective.setObjectivePhase(1)
	return