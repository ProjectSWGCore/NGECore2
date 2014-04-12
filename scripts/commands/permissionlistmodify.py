import sys

def setup():
    return
    
def run(core, actor, target, commandString):
	permissionType = 'Undetermined'
	commandArgs = commandString.split(' ')
	if len(commandArgs) > 3:
		permissionType = commandArgs[2]
	if permissionType=='ENTRY' | permissionType=='BAN':
		core.housingService.handlePermissionListModify(actor, target, commandString)
	if permissionType=='ADMIN' | permissionType=='HOPPER':	
		core.harvesterService.handlePermissionListModify(actor, target, commandString)
    return

	