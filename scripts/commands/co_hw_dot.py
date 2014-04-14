import sys

def setup():
	return
	
def run(core, actor, target, commandString):

	commandSvc = core.commandService
	
	weapon = core.objectService.getObject(actor.getWeaponId())
	
	if not weapon:
		return
		
	if not weapon.getWeaponType() == 12:
		return
		
	elementalType = weapon.getElementalType()
	commandName = ''
	
	if elementalType == 'heat':
		if actor.getLevel() >= 58:
			commandName = 'co_hw_dot_fire_1'
		if actor.getLevel() >= 60:
			commandName = 'co_hw_dot_fire_2'
		if actor.getLevel() >= 70:
			commandName = 'co_hw_dot_fire_3'
		if actor.getLevel() >= 80:
			commandName = 'co_hw_dot_fire_4'
		if actor.getLevel() >= 90:
			commandName = 'co_hw_dot_fire_5'
	elif elementalType == 'cold':
		if actor.getLevel() >= 58:
			commandName = 'co_hw_dot_cold_1'
		if actor.getLevel() >= 60:
			commandName = 'co_hw_dot_cold_2'
		if actor.getLevel() >= 70:
			commandName = 'co_hw_dot_cold_3'
		if actor.getLevel() >= 80:
			commandName = 'co_hw_dot_cold_4'
		if actor.getLevel() >= 90:
			commandName = 'co_hw_dot_cold_5'
	elif elementalType == 'acid':
		if actor.getLevel() >= 58:
			commandName = 'co_hw_dot_acid_1'
		if actor.getLevel() >= 60:
			commandName = 'co_hw_dot_acid_2'
		if actor.getLevel() >= 70:
			commandName = 'co_hw_dot_acid_3'
		if actor.getLevel() >= 80:
			commandName = 'co_hw_dot_acid_4'
		if actor.getLevel() >= 90:
			commandName = 'co_hw_dot_acid_5'
	elif elementalType == 'electricity':
		if actor.getLevel() >= 58:
			commandName = 'co_hw_dot_electrical_1'
		if actor.getLevel() >= 60:
			commandName = 'co_hw_dot_electrical_2'
		if actor.getLevel() >= 70:
			commandName = 'co_hw_dot_electrical_3'
		if actor.getLevel() >= 80:
			commandName = 'co_hw_dot_electrical_4'
		if actor.getLevel() >= 90:
			commandName = 'co_hw_dot_electrical_5'
	elif elementalType == 'energy':
		if actor.getLevel() >= 58:
			commandName = 'co_hw_dot_energy_1'
		if actor.getLevel() >= 60:
			commandName = 'co_hw_dot_energy_2'
		if actor.getLevel() >= 70:
			commandName = 'co_hw_dot_energy_3'
		if actor.getLevel() >= 80:
			commandName = 'co_hw_dot_energy_4'
		if actor.getLevel() >= 90:
			commandName = 'co_hw_dot_energy_5'
	elif elementalType =='kinetic':
		if actor.getLevel() >= 58:
			commandName = 'co_hw_dot_kinetic_1'
		if actor.getLevel() >= 60:
			commandName = 'co_hw_dot_kinetic_2'
		if actor.getLevel() >= 70:
			commandName = 'co_hw_dot_kinetic_3'
		if actor.getLevel() >= 80:
			commandName = 'co_hw_dot_kinetic_4'
		if actor.getLevel() >= 90:
			commandName = 'co_hw_dot_kinetic_5'
	
	if commandName == '':
		return
		
	combatCommand = commandSvc.getCommandByName(commandName).clone()
	
	if not combatCommand:
		return
		
	commandSvc.processCombatCommand(actor, target, combatCommand, 0, commandString) 
		
	return
	