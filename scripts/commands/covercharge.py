from services.sui import SUIWindow
from services.sui.SUIWindow import Trigger
from java.util import Vector
import sys

def setup():
    return
    
def run(core, actor, target, commandString):
	ghost = actor.getSlottedObject('ghost')
	if ghost.getProfession() != 'entertainer_1a':
		actor.sendSystemMessage('@performance:cc_no_skill', 0)
		return
	if commandString == '0':
		actor.setCoverCharge(0)
		actor.sendSystemMessage('@performance:cc_stop_charge', 0)
		return
	
	if commandString == None or commandString == '':
		window = core.suiService.createInputBox(2, '@performance:cc_set_title', '@performance:cc_set_prompt', actor, None, 10, handleCoverCharge)
		window.setProperty('txtInput:NumericInteger', 'true')
		window.setProperty('txtInput:MaxLength', '12')
		core.suiService.openSUIWindow(window)
		return
	else:
		try:
			actor.setCoverCharge(int(commandString))
			actor.sendSystemMessage('You are now charging a cover of ' + commandString + ' credit(s).', 0)
		except ValueError:
			return
		return
	return

def handleCoverCharge(actor, window, eventType, returnList):
	if eventType == 0 and len(returnList) == 1:
		charge = returnList.get(0)
		if charge == "0":
			actor.setCoverCharge(0)
			actor.sendSystemMessage('@performance:cc_stop_charge', 0)
			return
		else:
			actor.setCoverCharge(charge)
			actor.sendSystemMessage('You are now charging a cover of ' + returnList.get(0) + ' credits(s).', 0)
			return
		return
	return