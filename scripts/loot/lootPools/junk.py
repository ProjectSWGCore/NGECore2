
def itemNames():
	
	templates=['damaged_datapad','red_wiring','worklight_generic','antenna','brain','eye','gland','heart','stomach','armor_repair_device'] #10
	templates=templates+['brain','chassis_blueprint','comlink','explosive_dud','hyperdrive_unit','id_chip','impulse_detector','laser_trap'] #8
	templates=templates+['launcher_tube','ledger','magseal_detector','medical_console','medical_device','motor','power_output_analyzer','shield_module'] #8
	templates=templates+['software_module','survival_gear','underpowered_survey_pad','unidentified_serum_vial','used_notebook','weak_droid_battery'] #6
	return templates
	
def itemChances():
	chances=[3.125,3.125,3.125,3.125,3.125,3.125,3.125,3.125]
	chances=chances+[3.125,3.125,3.125,3.125,3.125,3.125,3.125,3.125]
	chances=chances+[3.125,3.125,3.125,3.125,3.125,3.125,3.125,3.125]
	chances=chances+[3.125,3.125,3.125,3.125,3.125,3.125,3.125,3.125]
	return chances #32