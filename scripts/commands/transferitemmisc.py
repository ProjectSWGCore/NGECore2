import sys
from engine.resources.scene import Quaternion

def setup():
        return
       
def run(core, actor, target, commandString):
        parsedMsg = commandString.split(' ', 3)
        objService = core.objectService
        containerID = long(parsedMsg[1])
        container = objService.getObject(containerID)
        actorContainer = actor.getContainer()
        
        if container == None: return
        
        if actorContainer != None and (container.getTemplate() == "object/cell/shared_cell.iff") & core.housingService.getPermissions(actor, actorContainer):
			target.getContainer().transferTo(actor, container, target)
			core.simulationService.teleport(target, actor.getPosition(), Quaternion(1,0,0,0), containerID)
			return
			
        elif target.getContainer().getTemplate() == "object/cell/shared_cell.iff" and core.housingService.getPermissions(actor, target.getContainer()):
			target.getContainer().transferTo(actor, container, target)
			return
			
        elif actorContainer != None and container.getTemplate() == "object/cell/shared_cell.iff":
            actor.sendSystemMessage("You do not have permission to access that container!", 0)
            return

        if core.equipmentService.canEquip(actor, target) is False:
            actor.sendSystemMessage('@error_message:insufficient_skill', 0)
            return
			
			
        if target and container and target.getContainer():
                oldContainer = target.getContainer()
               
                if container == oldContainer:
                        print 'Error: New container is same as old container.'
                        return;
               
		replacedObject = None
		slotName = None
		replacedObjects = []
		slotNames = None
	        if actor == container:
			slotNames = container.getSlotNamesForObject(target)
			for slotName in slotNames:
				object = container.getSlottedObject(slotName)
				if not object in replacedObjects and not object == None:
					replacedObjects.append(object)
		
                oldContainer.transferTo(actor, container, target)
               
                if actor == container:
                        if target.getTemplate().find('/wearables/') or target.getTemplate().find('/weapon/'):
                                core.equipmentService.equip(actor, target)
				for object in replacedObjects:
					core.equipmentService.unequip(actor, object)
				#path = 'scripts/' + target.getTemplate().rpartition('/')[0] + '/'        
                                #module = target.getTemplate().rpartition('/')[2].replace('shared_', '').replace('.iff', '')
                                #core.scriptService.callScript(path, 'equip', module, core, actor, target)
               	if container == actor.getSlottedObject('appearance_inventory'):
			actor.addObjectToAppearanceEquipList(target)
			for object in replacedObjects:
				actor.removeObjectFromAppearanceEquipList(object)
		
		if oldContainer == actor.getSlottedObject('appearance_inventory'):
			actor.removeObjectFromAppearanceEquipList(target)

                if actor == oldContainer:
                        if target.getTemplate().find('/wearables/') or target.getTemplate().find('/weapon/'):
                                #path = 'scripts/' + target.getTemplate().rpartition('/')[0] + '/'        
                                #module = target.getTemplate().rpartition('/')[2].replace('shared_', '').replace('.iff', '')
                                #core.scriptService.callScript(path, 'unequip', module, core, actor, target)
                                core.equipmentService.unequip(actor, target)

        return