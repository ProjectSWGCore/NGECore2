import sys

def setup(core, object):

	lootSpecification = [
		[
			['Junk',90],
			['Rifles',70],
			['ParentProbability',60]
		],
		[
			['Colorcrystal',100],
			['ParentProbability',50]
		]
	]
	object.setLootSpecification(lootSpecification)
	return