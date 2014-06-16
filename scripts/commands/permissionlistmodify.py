import sys

def setup():
    return

def run(core, actor, target, commandString):
	permissionType = 'Undetermined'
	commandArgs = commandString.split(' ')
	target = core.housingService.getClosestStructureWithAdminRights(actor)
	if not target:
		return
	if target.getTemplate().startswith('object/building'):
		core.housingService.handlePermissionListModify(actor, target, commandString)
	elif target.getTemplate().startswith('object/installation'):
		core.harvesterService.handlePermissionListModify(actor, target, commandString)
    	return

	