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
        
        if not 'cell' in container.getTemplate() and container.isFull():
            actor.sendSystemMessage('@container_error_message:container03', 0)
            return

        if container.getTemplate().startswith("object/tangible/inventory/shared_lightsaber_inventory") or target.getContainer().getTemplate().startswith("object/tangible/inventory/shared_lightsaber_inventory"):	
            core.equipmentService.calculateLightsaberAttributes(actor, target, container)
            return;

        if target.getTemplate() == 'object/tangible/item/shared_loot_cash.iff':
            core.lootService.handleCreditPickUp(actor,target)
            core.objectService.destroyObject(target)
            return
			
        if target.isLootItem():
            target.setLootItem(0)
            actor.sendSystemMessage('You looted ' + target.getObjectName().getStfValue() + ' from corpse.', 0)
        
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

        canEquip = core.equipmentService.canEquip(actor, target)
		
        if canEquip[0] is False and container == actor:
            actor.sendSystemMessage(canEquip[1], 0)
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
               
                for object in replacedObjects:
					core.equipmentService.unequip(actor, object)
               
                if actor == container:
                        if target.getTemplate().find('/wearables/') or target.getTemplate().find('/weapon/'):
                                core.equipmentService.equip(actor, target)
				
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
