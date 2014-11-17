import sys
from services.sui import SUIWindow
from services.sui import SUIService
from services.sui.SUIWindow import Trigger
from services.sui.SUIService import ListBoxType
from java.util import Vector
from java.util import HashMap
from resources.datatables import Posture

def setup():
    return

def run(core, actor, target, commandString):
	global suiWindow
	global actorObject
	global entSvc
	actorObject = actor
	entSvc = core.entertainmentService
	
	instrumentCode = entSvc.getInstrumentAudioId(actor)
		
	#print('instrumentCode: ' , instrumentCode)
	if instrumentCode > 0:
		#Parse command string
		if len(commandString) > 0:
			params = commandString.split(" ")
			performance = entSvc.getPerformance(params[0], instrumentCode)
			#print('line number: ', performance.getLineNumber())
			if performance is None:
				#Not a proper song
				actor.sendSystemMessage('@performance:music_invalid_song', 0)
				return

			#Handle lack of skill
			if not entSvc.canPerform(actor, performance):
				actor.sendSystemMessage('@performance:music_lack_skill_self',0)
				return
			startMusic(core, actor, performance.getLineNumber())
		else:
			availableSongs = entSvc.getAvailableSongs(actor, instrumentCode)
			
			suiSvc = core.suiService
			suiWindow = suiSvc.createListBox(ListBoxType.LIST_BOX_OK_CANCEL, "@performance:select_song", "@performance:available_songs", availableSongs, actor, None, 10)
			
			returnList = Vector()
			returnList.add("List.lstList:SelectedRow")
			suiWindow.addHandler(0, '', Trigger.TRIGGER_OK, returnList, handleStartMusic)
			
			suiSvc.openSUIWindow(suiWindow)
	else:
		#Equipped item is not an instrument
		actor.sendSystemMessage('@performance:music_no_instrument', 0)
		pass
	'''
	NOTE: animation files are "all_b_dnc_musician_<instrument-name>_loop.ans"
	NOTE: flourish animation files are "all_b_dnc_musician_<instrument-name>_sp_8.ans"
	This information doesn't seem to be needed...
	'''
	pass
	
def handleStartMusic(core, owner, eventType, returnList):
	item = suiWindow.getMenuItems().get(int(returnList.get(0)))
	print(item.getObjectId())
	startMusic(core, actorObject, item.getObjectId())
	pass

def startMusic(core, actor, performanceLineNumber):
	if actor.getPosture() == 0x09:
		actor.sendSystemMessage('@performance:already_performing_self', 0)
		pass
		
	if not actor.getPerformanceWatchee():
		#this also notifies the client with a delta4
		actor.setPerformanceWatchee(actor)
		actor.addSpectator(actor)
		
	actor.setPosture(0x09)
	
	#Turn on XP gain
	playerObject = actor.getSlottedObject('ghost')
	if playerObject and playerObject.getProfession() == "entertainer_1a" and actor.getLevel() != 90:
		entSvc.startPerformanceExperience(actor)
		
	performance = entSvc.getPerformanceByIndex(performanceLineNumber)#TODO: Refactor to not need this
	audioId = performance.getInstrumentAudioId()
	
	songAnim = "music_0"
	if audioId < 7:
		songAnim = "music_3"	#Horns
	elif audioId < 8:
		songAnim = "music_1"	#Bandfill
	elif audioId < 9:
		songAnim = "music_4"	#Omnibox
	elif audioId < 10:
		songAnim = "music_2"	#Nargalon
	elif audioId < 12:
		songAnim = "music_5"	#String instruments
	elif audioId < 14:
		songAnim = "music_3"	#More horns
	elif audioId < 15:
		songAnim = "music_4"	#downeybox (Don't think this was ever added)
		
	#print(songAnim)
		
	
	actor.sendSystemMessage('@performance:music_start_self',0)
	#entSvc.startPerformance(actor, performance.getLineNumber(), -842249156, "dance_0", 0)#TODO: Figure out what exactly goes in parameter slots 2 and 3
	#entSvc.startPerformance(actor, performance.getLineNumber(), -842249156, anim, 0)#TODO: Figure out what exactly goes in parameter slots 2 and 3
	entSvc.startPerformance(actor, performanceLineNumber+1, -842249156, songAnim, 0)#TODO: Figure out what exactly goes in parameter slots 2 and 3
	'''
	Bandfill = music_1
	Nargalon = music_2
	(Some flute-ish)[perhaps traz] = music_3
	Omnibox = music_4
	Mandoviol = music_5
	'''
	
	pass
	