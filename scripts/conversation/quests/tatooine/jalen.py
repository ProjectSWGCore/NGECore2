from resources.common import ConversationOption
from resources.common import OutOfBand
from resources.common import ProsePackage
from java.util import Vector
import sys

def startConversation(core, actor, npc):
	player = actor.getPlayerObject()
	if player is None:
		return
	quest = player.getQuest('smuggle_generic_1')
	if quest is None or quest.isCompleted():
		core.conversationService.sendStopConversation(actor, npc, 'conversation/fence', 's_10')
	elif quest.getActiveTask() == 2:
		core.conversationService.sendStopConversation(actor, npc, 'conversation/fence', 's_5')
		core.questService.completeActiveTask(actor, quest)
	else:
		core.conversationService.sendStopConversation(actor, npc, 'conversation/fence', 's_10')
	return

def endConversation(core, actor, npc):
	player = actor.getPlayerObject()
	if player is None:
		return
	quest = player.getQuest('smuggle_generic_1')
	if quest is None:
		core.conversationService.sendStopConversation(actor, npc, 'conversation/fence', 's_48')
	return
