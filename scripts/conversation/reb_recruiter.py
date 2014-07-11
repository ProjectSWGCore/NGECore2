from resources.common import ConversationOption
from resources.common import OutOfBand
from resources.common import ProsePackage
from resources.common import RadialOptions
from resources.datatables import PvpStatus
from resources.datatables import FactionStatus

from services.sui import SUIWindow
from services.sui.SUIWindow import Trigger

from java.util import Vector
import sys


impRecruiterRef = 0

def startConversation(core, actor, npc):
	global coreRef
	global impRecruiterRef
	coreRef = core
	impRecruiterRef = npc
	convSvc = core.conversationService
	faction = actor.getFaction()
	factionStatus = actor.getFactionStatus()
	
	if actor.getPvpStatus(PvpStatus.GoingOvert) or actor.getPvpStatus(PvpStatus.GoingCovert):
		core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_rebel', 's_364')
		return	
	elif actor.getFaction() != 'rebel' and actor.getFaction() != 'imperial':
		convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_334'))
		
		options = Vector()
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_348'), 0))
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_83'), 1))
		convSvc.sendConversationOptions(actor, npc, options, handleFirstScreen)
		
		return
	elif actor.getFaction() == 'rebel' and actor.getFactionStatus() == FactionStatus.OnLeave:
		convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_216'))
		
		options = Vector()
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_49'), 0))
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_330'), 1))
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_248'), 2))
		convSvc.sendConversationOptions(actor, npc, options, handleFirstScreen)
		
		return
	elif actor.getFaction() == 'rebel' and actor.getFactionStatus() == FactionStatus.Combatant:
		convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_216'))
		
		options = Vector()
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_49'), 0))
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_330'), 1))
		options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_248'), 2))
		convSvc.sendConversationOptions(actor, npc, options, handleFirstScreen)	
		
		return
	elif actor.getFactionStatus() == FactionStatus.SpecialForces:
		convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_216'))
		
		options = Vector()
		options.add(ConversationOption(OutOfBand.ProsePackage('conversation/faction_recruiter_rebel', 's_49'), 0))
		options.add(ConversationOption(OutOfBand.ProsePackage('conversation/faction_recruiter_rebel', 's_330'), 1))
		options.add(ConversationOption(OutOfBand.ProsePackage('conversation/faction_recruiter_rebel', 's_248'), 2))
		convSvc.sendConversationOptions(actor, npc, options, handleFirstScreen)	
		
		return
	elif actor.getFaction() == 'imperial':
		npc.setCurrentAnimation('all_b_emt_backhand')
		core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_rebel', 's_230')
		return
	return

def handleFirstScreen(core, actor, npc, selection):
	convSvc = core.conversationService
	
	if actor.getFaction() != 'rebel' and actor.getFaction() != 'imperial':
		if selection == 0:
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_352'))
			
			options = Vector()
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_354'), 0))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_360'), 1))
			convSvc.sendConversationOptions(actor, npc, options, handleSecondScreen)
			
			return
		if selection == 1:
			return
		if selection == 2:
			core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_rebel', 's_362')	
			return
		return
	elif actor.getFaction() == 'rebel' and actor.getFactionStatus() == FactionStatus.OnLeave:
		if selection == 0:
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_50'))
			
			options = Vector()
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_95'), 0))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_93'), 1))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_74'), 2))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_360'), 3))
			convSvc.sendConversationOptions(actor, npc, options, handleSecondScreen)
			
			return
		if selection == 1:
			percent = core.guildService.getGuildObject().getCurrentServerGCWTotalPercentMap().get(actor.getPlanet().getName()).getPercent().intValue()
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage("@conversation/faction_recruiter_rebel:s_332", percent, "TO", str(100 - percent)))
			return
		if selection == 2:
			return
		return
	elif actor.getFaction() == 'rebel' and actor.getFactionStatus() == FactionStatus.Combatant:	
		if selection == 0:
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('conversation/faction_recruiter_rebel', 's_50'))
			
			options = Vector()
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_86'), 0))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_93'), 1))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_74'), 2))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_360'), 3))
			convSvc.sendConversationOptions(actor, npc, options, handleSecondScreen)
			
			return
		if selection == 1:
			percent = core.guildService.getGuildObject().getCurrentServerGCWTotalPercentMap().get(actor.getPlanet().getName()).getPercent().intValue()
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage("@conversation/faction_recruiter_rebel:s_332", percent, "TO", str(100 - percent)))
			return
		if selection == 2:
			core.scriptService.callScript("scripts/", "gcw_rewards_rebel", "handleRebelItems1", core, actor); 
			core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_rebel', 's_75')
			return
		return
	elif actor.getFaction() == 'rebel' and actor.getFactionStatus() == FactionStatus.SpecialForces:	
		if selection == 0:
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_50'))
			
			options = Vector()
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_90'), 0))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_95'), 1))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_78'), 2))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_360'), 3))
			convSvc.sendConversationOptions(actor, npc, options, handleSecondScreen)
			
			return
		if selection == 1:
			percent = core.guildService.getGuildObject().getCurrentServerGCWTotalPercentMap().get(actor.getPlanet().getName()).getPercent().intValue()
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage("@conversation/faction_recruiter_rebel:s_332", percent, "TO", str(100 - percent)))
			return
		if selection == 2:
			return
		return
			
def handleSecondScreen(core, actor, npc, selection):
	convSvc = core.conversationService
	
	
	if actor.getFaction() != 'rebel' and actor.getFaction() != 'imperial':
		if selection == 0:
			core.factionService.join(actor, 'rebel')
			core.factionService.changeFactionStatus(actor, FactionStatus.OnLeave)
			
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_99'))
			
			options = Vector()
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_358'), 0))
			core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_rebel', 's_356')
			
			return
		if selection == 1:
			core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_rebel', 's_362')	
			return
		return
	elif actor.getFaction() == 'rebel' and actor.getFactionStatus() == FactionStatus.OnLeave:
		if selection == 0:
			core.factionService.changeFactionStatus(actor, FactionStatus.Combatant)
			core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_rebel', 's_224')
			return
		if selection == 1:
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_64'))
			
			options = Vector()
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_66'), 0))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_226'), 1))
			convSvc.sendConversationOptions(actor, npc, options, handleFourthScreen)
			
			return
		if selection == 2:
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_76'))
			
			options = Vector()
			options.add(ConversationOption(outOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_78'), 0))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_75'), 1))
			convSvc.sendConversationOptions(actor, npc, options, handleThirdScreen)
			
			return
		if selection == 3:
			core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_rebel', 's_97')
			return
		return	
	elif actor.getFaction() == 'rebel' and actor.getFactionStatus() == FactionStatus.Combatant:
		if selection == 0:
			core.factionService.changeFactionStatus(actor, FactionStatus.SpecialForces)
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_88'))
			
			options = Vector()
			options.add(ConversationOption(outOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_90'), 0))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_94'), 1))
			convSvc.sendConversationOptions(actor, npc, options, handleFithScreen)
			
			return
		if selection == 1:
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_64'))
			
			options = Vector()
			options.add(ConversationOption(outOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_66'), 0))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_226'), 1))
			convSvc.sendConversationOptions(actor, npc, options, handleFourthScreen)
			
			return
		if selection == 2:
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_76'))
			
			options = Vector()
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_78'), 0))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_360'), 1))
			convSvc.sendConversationOptions(actor, npc, options, handleThirdScreen)
			
			return
		if selection == 3:
			return
		return	
	elif actor.getFaction() == 'rebel' and actor.getFactionStatus() == FactionStatus.SpecialForces:
		if selection == 0:
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('conversation/faction_recruiter_rebel:s_88'))
			
			options = Vector()
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_90'), 0))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_94'), 1))
			convSvc.sendConversationOptions(actor, npc, options, handleFithScreen)
			
			return
		if selection == 1:
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_54'))
			
			options = Vector()
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_56'), 0))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_360'), 1))
			convSvc.sendConversationOptions(actor, npc, options, handleSixthScreen)
			
			return
		if selection == 2:
			convSvc.sendConversationMessage(actor, npc, OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_76'))
				
			options = Vector()
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_74'), 0))
			options.add(ConversationOption(OutOfBand.ProsePackage('@conversation/faction_recruiter_rebel:s_360'), 1))
			convSvc.sendConversationOptions(actor, npc, options, handleThirdScreen)
			
			return
		if selection == 3:
			return	
		return

def handleThirdScreen(core, actor, npc, selection):
	if selection == 0:
		core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_rebel', 's_58')	
		core.factionService.resign(actor)
		return
	if selection == 1:
		core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_rebel', 's_80')
		return	
	return

def handleFourthScreen(core, actor, npc, selection):
	if selection == 0:
		core.factionService.changeFactionStatus(actor, FactionStatus.SpecialForces)
		core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_rebel', 's_224')
		return
	if selection == 1:
		core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_rebel', 's_72')
		return
	return

def handleFithScreen(core, actor, npc, selection):
	if selection == 0:
		core.factionService.changeFactionStatus(actor, FactionStatus.OnLeave)
		core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_rebel', 's_58')
	return

def handleSixthScreen(core, actor, npc, selection):
	if selection == 0:
		core.factionService.changeFactionStatus(actor, FactionStatus.Combatant)
		core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_rebel', 's_92')
		return
	if selection == 1:
		core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_rebel', 's_57')
		return
	return

def endConversation(core, actor, npc):
	core.conversationService.sendStopConversation(actor, npc, 'conversation/faction_recruiter_imperial', 's_304')
	return