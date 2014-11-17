#Base file generated using Conversation Script Creator for ProjectSWG
from resources.common import ConversationOption
from resources.common import OutOfBand
from resources.common import ProsePackage
from java.util import Vector

import sys

def startConversation(core, actor, npc):
	
	options = new Vector()
	
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/conversation/gate_keeper_quarantine_zone:s_12'), 0)
	core.conversationService.sendConversationOptions(actor, npc, handleOptionScreen1)
	core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/conversation/gate_keeper_quarantine_zone:s_10'))
	return

# Handle Response for conversation/gate_keeper_quarantine_zone:s_10
def handleOptionScreen1(core, actor, npc, selection):

	if selection == 0:
		# conversation/gate_keeper_quarantine_zone:s_12
		options = new Vector()
		
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/conversation/gate_keeper_quarantine_zone:s_16'), 0)
		core.conversationService.sendConversationOptions(actor, npc, handleOptionScreen2)
		core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/conversation/gate_keeper_quarantine_zone:s_14')
		return

	return

# Handle Response for conversation/gate_keeper_quarantine_zone:s_14
def handleOptionScreen2(core, actor, npc, selection):

	if selection == 0:
		# conversation/gate_keeper_quarantine_zone:s_16
		core.conversationService.sendStopConversation(actor, npc, 'conversation/conversation/gate_keeper_quarantine_zone', 's_19')
		return

	return

