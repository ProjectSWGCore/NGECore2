from resources.common import ConversationOption
from resources.common import OutOfBand
from resources.common import ProsePackage
from java.util import Vector
import sys

def startConversation(core, actor, npc):
	convSvc = core.conversationService
	
	core.conversationService.sendStopConversation(actor, npc, 'conversation/mos_eisley_mayor', 's_117')
	return

def endConversation(core, actor, npc):
	core.conversationService.sendStopConversation(actor, npc, 'conversation/mos_eisley_mayor', 's_117')
	return