import sys

def setup():
    return

def run(core, actor, target, commandString):
	#print("Up the Irons!")
	entSvc = core.entertainmentService
	instrumentObj = entSvc.getInstrument(actor)
	#TODO: Determine if player has an instrument
	if instrumentObj is not None:
		#TODO: If player does have an instrument then determine what type of instrument the player has
		instrumentStfName = instrumentObj.getStfName()
		instrumentCode = -1
		#TODO: If type is traz then audioID is 1
		if instrumentStfName == "obj_traz_classic":
			instrumentCode = 1
		#TODO: Else if type is slitherhorn then audioID is 2
		elif instrumentStfName == "obj_slitherhorn_classic":
			instrumentCode = 2
		#TODO: Else if type is fanfar then audioID is 3
		elif instrumentStfName == "obj_fanfar_classic":
			instrumentCode = 3
		#TODO: Else if type is flutedroopy then audioID is 4
		#FIXME: I don't know what this is yet, but I suspect it's "obj_chidinkalu_horn_classic"

		#TODO: Else if type is kloohorn then audioID is 5
		elif instrumentStfName == "obj_kloo_horn_classic":
			instrumentCode = 5
		#TODO: Else if type is fizz then audioID is 6
		elif instrumentStfName == "obj_fizzz_classic":
			instrumentCode = 6
		#TODO: Else if type is bandfill then audioID is 7
		elif instrumentStfName == "obj_bandfill_classic":
			instrumentCode = 7
		
		#TODO: Else if type is omnibox then audioID is 8
		#FIXME: This instrument isn't equipped when played
				
		#TODO: Else if type is nalargon then audioID is 9
		#FIXME: This instrument isn't equipped when played
				
		#TODO: Else if type is mandoviol then audioID is 10
		elif instrumentStfName == "obj_mandoviol_classic":
			instrumentCode = 10
		#TODO: Else if type is xantha then audioID is 11
		elif instrumentStfName == "xantha_n":
			instrumentCode = 11
		#TODO: Else if type is flangedjessoon then audioID is 12
		elif instrumentStfName == "obj_jessoon":
			instrumentCode = 12
		#TODO: Else if type is valahorn then audioID is 13
		elif instrumentStfName == "obj_valahorn":
			instrumentCode = 13
		#TODO: Else if type is downeybox then audioID is 14
		#FIXME: This instrument isn't equipped when played
		
		print("instrumentCode: " , instrumentCode)
		if instrumentCode > 0:
			#TODO: Parse command string
			if len(commandString) > 0:
				params = commandString.split(" ")
				performance = entSvc.getPerformance(params[0], instrumentCode)
				#print(performance.getPerformanceName(), performance.getInstrumentAudioId())
				startMusic(core, actor, performance, entSvc)
				
		else:
			#TODO: In game error message
			pass
	else:
		#TODO: In game error message
		pass
	'''
	NOTE: animation files are "all_b_dnc_musician_<instrument-name>_loop.ans"
	NOTE: flourish animation files are "all_b_dnc_musician_<instrument-name>_sp_8.ans"
	This information doesn't seem to be needed...
	'''
	pass
def startMusic(core, actor, performance, entSvc):
	print (actor.getPosture())
	if performance is None:
		#TODO: Client error message
		return
	#TODO: Handle lack of skill
	if actor.getPosture == 0x09:
		#TODO: Client error 
		#return
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
	
	entSvc.startPerformance(actor, performance.getLineNumber(), -842249156, "dance_0", 0)#TODO: Figure out what exactly goes in parameter slots 2 and 3
	pass
	