import sys

def isAlly(ally):
	if ally == 'panshee_tribe':
		return 1
	return 0

def isEnemy(enemy):
	if enemy == 'naboo_security_force' or enemy == 'nym' or enemy == 'imperial' or enemy == 'jabba' or enemy == 'borvo' or enemy == 'corsec' or enemy == 'narmle':
		return 1
	return 0