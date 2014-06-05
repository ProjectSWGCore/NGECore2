from resources.common import ConversationOption
from resources.common import OutOfBand
from resources.common import ProsePackage
from java.util import Vector
import time
import sys

def startConversation(core, actor, npc):
	convSvc = core.conversationService
	
	probotRequester = core.objectService.getObject(long(npc.getAttachment('probotRequester')))

	if probotRequester is None:
		core.objectService.destroyObject(npc)
		return
	
	if probotRequester.getObjectId() != actor.getObjectId():
		convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/bounty_probot:s_6'))
		return
	
	mission = core.objectService.getObject(long(npc.getAttachment('attachedMission')))
	
	if mission is None:
		convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/bounty_probot:s_4'))
		return
	
	objective = mission.getObjective()
	
	if objective is None:
		convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/bounty_probot:s_4'))
		return
	
	convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/bounty_probot:s_5'))
	
	options = Vector()
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/bounty_probot:s_8'), 0))
	convSvc.sendConversationOptions(actor, npc, options, handleBioTransmit)
	return

def handleBioTransmit(core, actor, npc, selection):
	core.conversationService.handleEndConversation(actor, npc)
	actor.sendSystemMessage('@mission/mission_generic:probe_droid_takeoff', 0)

	npc.setPosture(8)
	
	time.sleep(7)
	objective = core.objectService.getObject(long(npc.getAttachment('attachedMission'))).getObjective()
	objective.beginArakydUpdate(actor)
	core.objectService.destroyObject(npc)
	return

def endConversation(core, actor, npc):
	core.conversationService.sendStopConversation(actor, npc, '', '')
	return
