
def itemNames():
	
	templates=['damaged_datapad','red_wiring','worklight_generic','armor_repair_device'] #4
	templates=templates+['chassis_blueprints','comlink','explosive_dud','hyperdrive_unit','id_chip','impulse_detector','laser_trap'] #7
	templates=templates+['launcher_tube','ledger','magseal_detector','medical_console','medical_device','motor','power_output_analyzer','shield_module'] #8
	templates=templates+['software_module','survival_gear','underpowered_survey_pad','unidentified_serum_vial','used_notebook','weak_droid_battery'] #6
	return templates
	
def itemChances():
	chances=[4,4,4,4]
	chances=chances+[4,4,4,4,4,4,4]
	chances=chances+[4,4,4,4,4,4,4,4]
	chances=chances+[4,4,4,4,4,4]
	return chances 