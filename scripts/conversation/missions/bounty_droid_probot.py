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
		prose = ProsePackage('conversation/bounty_probot', 's_6')
		outOfBand = OutOfBand()
		outOfBand.addProsePackage(prose)
		convSvc.sendConversationMessage(actor, npc, outOfBand)
		return
	
	mission = core.objectService.getObject(long(npc.getAttachment('attachedMission')))
	
	if mission is None:
		prose = ProsePackage('conversation/bounty_probot', 's_4')
		outOfBand = OutOfBand()
		outOfBand.addProsePackage(prose)
		convSvc.sendConversationMessage(actor, npc, prose)
		return
	
	objective = mission.getObjective()
	
	if objective is None:
		prose = ProsePackage('conversation/bounty_probot', 's_4')
		outOfBand = OutOfBand()
		outOfBand.addProsePackage(prose)
		convSvc.sendConversationMessage(actor, npc, prose)
		return
	
	prose = ProsePackage('conversation/bounty_probot', 's_5')
	outOfBand = OutOfBand()
	outOfBand.addProsePackage(prose)
	convSvc.sendConversationMessage(actor, npc, outOfBand)
	
	options = Vector()
	optionProse = ProsePackage('conversation/bounty_probot', 's_8')
	optionOOB = OutOfBand()
	optionOOB.addProsePackage(optionProse)
	options.add(ConversationOption(optionOOB, 0))
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
