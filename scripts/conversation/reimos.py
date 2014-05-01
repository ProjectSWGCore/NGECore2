from resources.common import ConversationOption
from resources.common import OutOfBand
from resources.common import ProsePackage
from java.util import Vector
import sys

def startConversation(core, actor, npc):
	convSvc = core.conversationService
	
	core.conversationService.sendStopConversation(actor, npc, 'conversation/tatooine_eisley_reimos_v2', 's_36')
	return

def endConversation(core, actor, npc):
	core.conversationService.sendStopConversation(actor, npc, 'conversation/tatooine_eisley_reimos_v2', 's_36')
	return