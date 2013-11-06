import sys
 
def setup():
        return
       
def run(core, actor, target, commandString):
        parsedMsg = commandString.split(' ', 3)
        objService = core.objectService
        containerID = long(parsedMsg[1])
        container = objService.getObject(containerID)
        if target and container and target.getContainer():
                oldContainer = target.getContainer()
               
                if container == oldContainer:
                        print 'Error: New container is same as old container.'
                        return;
               
		replacedObject = None
		slotName = None
	        if actor == container:
			slotName = container.getSlotNameForObject(target)
			replacedObject = container.getSlottedObject(slotName)
	       
                oldContainer.transferTo(actor, container, target)
               
                if actor == container:
                        if target.getTemplate().find('/wearables/') or target.getTemplate().find('/weapon/'):
                                core.equipmentService.equip(actor, target, replacedObject)
				#path = 'scripts/' + target.getTemplate().rpartition('/')[0] + '/'        
                                #module = target.getTemplate().rpartition('/')[2].replace('shared_', '').replace('.iff', '')
                                #core.scriptService.callScript(path, 'equip', module, core, actor, target)
               
                if actor == oldContainer:
                        if target.getTemplate().find('/wearables/') or target.getTemplate().find('/weapon/'):
                                #path = 'scripts/' + target.getTemplate().rpartition('/')[0] + '/'        
                                #module = target.getTemplate().rpartition('/')[2].replace('shared_', '').replace('.iff', '')
                                #core.scriptService.callScript(path, 'unequip', module, core, actor, target)
                                core.equipmentService.unequip(actor, target)

        return