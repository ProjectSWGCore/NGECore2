from resources.common import ConversationOption
from resources.common import OutOfBand
from resources.common import ProsePackage
from java.util import Vector
import sys
# TODO: Add teleport line
def startConversation(core, actor, npc):
	convSvc = core.conversationService
	
	convSvc.conversationService.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/arven_wendik:s_4')) # I'm too busy to talk.
	
	options = new Vector()
	options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/padawan_kill_falumpaset_01 1'), 0) # I see. Well, thanks anyway.
	
	convSvc.sendConversationOptions(actor, npc, options, handleFirstScreen)
	return

#def handleOptionScreen1(core, actor, npc, selection):

	#if selection == 0:
		# New Conversation Response 4
		#return

	#return
