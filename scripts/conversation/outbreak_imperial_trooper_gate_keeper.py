#Base file generated using Conversation Script Creator for ProjectSWG
from resources.common import ConversationOption
from resources.common import OutOfBand
from resources.common import ProsePackage
from java.util import Vector

import sys

def startConversation(core, actor, npc):
	options = new Vector()
	#options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/New Conversation Response 4'), 0)
	#core.conversationService.sendConversationOptions(actor, npc, handleOptionScreen1)
	core.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/arven_wendik:s_4')) # I'm too busy to talk.
	return

# Handle Response for Begin Conversationdef handleOptionScreen1(core, actor, npc, selection):

	if selection == 0:
		# New Conversation Response 4
		return

	return

