import sys
from engine.resources.scene import Point3D
from resources.datatables import GalaxyStatus
from services.sui.SUIService import MessageBoxType
from services.sui.SUIWindow import Trigger
from java.util import Vector
import main.NGECore
	
def setup():
	return
	
def run(core, actor, target, commandString):
	core.scriptService.callScript("scripts/commands/", "server", "serverCommand", core, actor);
	return
	
def serverCommand (core, owner):
	actor = owner.getSlottedObject('ghost')
	window = core.suiService.createSUIWindow('Script.listBox', owner, owner, 0)
	window.setProperty('bg.caption.lblTitle:Text', 'ProjectSWG')
	window.setProperty('Prompt.lblPrompt:Text', 'Server Commands')
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')	
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')	
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, serverCommandCallback)
	window.addListBoxMenuItem('Lock Server', 0)
	window.addListBoxMenuItem('Unlock Server', 1)
	window.addListBoxMenuItem('Shutdown Server (15 Minute Countdown)', 2)
	window.addListBoxMenuItem('Stop Server (10 Seconds)', 3)
	window.addListBoxMenuItem('Active Connections', 4)	
	core.suiService.openSUIWindow(window)
def serverCommandCallback(owner, window, eventType, returnList):
		
	if returnList.size()==0:
		owner.sendSystemMessage('NULL', 0)	
		return
	if returnList.get(0)=='0':
		lockedHandler(owner)
		return
	if returnList.get(0)=='1':
		onlineHandler(owner)
		return
	if returnList.get(0)=='2':
		shutdownHandler(owner)
		return
	if returnList.get(0)=='3':
		stopHandler(owner)
		return
	if returnList.get(0)=='4':
		connectionHandler(owner)
	return
	
def lockedHandler(owner):
	core = main.NGECore.getInstance()
	core.setGalaxyStatus(GalaxyStatus.Locked)
	owner.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF Server lockServer: Command completed successfully. Server is now in Locked Status.', 0)
	return
		
def onlineHandler(owner):
	core = main.NGECore.getInstance()	
	core.setGalaxyStatus(GalaxyStatus.Online)
	owner.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF Server unlockServer: Command completed successfully. Server is now in Online Status.', 0)
	return

def shutdownHandler(owner):
	core = main.NGECore.getInstance()
	core.initiateShutdown()
	owner.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF Server shutdown: Command completed successfully. Server shutdown initiated.', 0)
	return

def stopHandler(owner):
	core = main.NGECore.getInstance()
	core.initiateStop()
	owner.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF Server stop: Command completed successfully. Emergency server shutdown initiated.', 0)
	return

def connectionHandler(owner):
	core = main.NGECore.getInstance()
	clients = str(core.getActiveZoneClients())
	owner.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF Active Connections: Command completed successfully.', 0)
	owner.sendSystemMessage('There are currently ' + clients + ' Characters connected to the Galaxy.', 0)
	return