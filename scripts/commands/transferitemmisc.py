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
		
		if container.getTemplate().startswith("object/tangible/inventory/shared_lightsaber_inventory") or (target and target.getContainer().getTemplate().startswith("object/tangible/inventory/shared_lightsaber_inventory")):	
			core.equipmentService.calculateLightsaberAttributes(actor, target, container)
			return;
		
		if target.getTemplate() == 'object/tangible/item/shared_loot_cash.iff':
			core.lootService.handleCreditPickUp(actor,target)
			if target.getGrandparent().getInventoryItemCount()==1:
				core.lootService.setLooted(actor,target.getGrandparent())
			core.objectService.destroyObject(target)
			return
		
		if target.isLootItem():
			target.setLootItem(0)
			actor.sendSystemMessage('You looted ' + target.getObjectName().getStfValue() + ' from corpse.', 0)
			#actor.sendSystemMessage('container.getInventoryItemCount() %s' % target.getGrandparent().getInventoryItemCount(), 0)
			if target.getGrandparent().getInventoryItemCount()==1:
				core.lootService.setLooted(actor,target.getGrandparent())
		
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
				
				if object != None:
					container.transferTo(actor, container, object)	
						
		oldContainer.transferTo(actor, container, target)
		
		if oldContainer == actor.getSlottedObject('appearance_inventory'):
			actor.removeObjectFromAppearanceEquipList(target)
		
		if container == actor.getSlottedObject('appearance_inventory'):
			actor.addObjectToAppearanceEquipList(target)		

		return
