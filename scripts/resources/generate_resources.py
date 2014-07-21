from main import NGECore
from services.resources import ResourceService
from services.resources.ResourceService import addResource
from services.resources.ResourceService import createResource
from resources.objects.resource import ResourceRoot
from array import array
import sys

def generate(core):
    generate1(core)
    generate2(core)
    generate3(core)
    generate4(core)
    generate5(core)
    generate6(core)
    generate7(core)
    generate8(core)
    generate9(core)
    generate10(core)
    generate11(core)
    generate12(core)
    generate13(core)
    generate14(core)
    

def generate1(core):
    res = core.resourceService
    
    min = array('h', [1, 300, 1, 200, 300, 300, 300, 0, 0, 1, 0])
    max = array('h', [174, 408, 174, 330, 452, 430, 430, 1, 1, 1000, 1])
    res.createResource("aluminum_titanium", "Aluminum", "Titanium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [94, 358, 94, 270, 382, 370, 370, 0, 0, 1, 0])
    max = array('h', [307, 492, 307, 430, 568, 530, 530, 1, 1, 1000, 1])
    res.createResource("aluminum_agrinium", "Aluminum", "Agrinium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [227, 442, 227, 370, 498, 470, 470, 0, 0, 1, 0])
    max = array('h', [440, 575, 440, 530, 685, 630, 630, 1, 1, 1000, 1])
    res.createResource("aluminum_chromium", "Aluminum", "Chromium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [361, 525, 361, 470, 615, 570, 570, 0, 0, 1, 0])
    max = array('h', [574, 658, 574, 630, 802, 730, 730, 1, 1, 1000, 1])
    res.createResource("aluminum_duralumin", "Aluminum", "Duralumin", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [494, 608, 494, 570, 732, 670, 670, 0, 0, 1, 0])
    max = array('h', [707, 742, 707, 730, 918, 830, 830, 1, 1, 1000, 1])
    res.createResource("aluminum_linksteel", "Aluminum", "Link-Steel", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [672, 692, 627, 670, 848, 770, 770, 0, 0, 1, 0])
    max = array('h', [800, 800, 800, 800, 1000, 900, 900, 1, 1, 1000, 1])
    res.createResource("aluminum_phrik", "Aluminum", "Phrik", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [1, 500, 1, 200, 500, 300, 300, 0, 0, 1, 0])
    max = array('h', [116, 572, 102, 265, 572, 372, 372, 1, 1, 1000, 1])
    res.createResource("copper_desh", "Copper", "Desh", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [63, 539, 55, 235, 539, 339, 339, 0, 0, 1, 0])
    max = array('h', [205, 628, 180, 315, 628, 428, 428, 1, 1, 1000, 1])
    res.createResource("copper_thallium", "Copper", "Thallium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [152, 594, 133, 285, 594, 394, 394, 0, 0, 1, 0])
    max = array('h', [294, 683, 257, 365, 683, 483, 483, 1, 1, 1000, 1])
    res.createResource("copper_beyrllius", "Copper", "Beyrllius", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [241, 650, 211, 335, 650, 450, 450, 0, 0, 1, 0])
    max = array('h', [383, 739, 335, 415, 739, 539, 593, 1, 1, 1000, 1])
    res.createResource("copper_codoan", "Copper", "Codoan", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [329, 706, 288, 385, 706, 506, 506, 0, 0, 0, 0])
    max = array('h', [472, 794, 413, 465, 794, 594, 594, 1, 1, 1000, 1])
    res.createResource("copper_diatium", "Copper", "Diatium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [418, 761, 366, 435, 761, 561, 561, 0, 0, 1, 0])
    max = array('h', [560, 850, 490, 515, 850, 650, 650, 1, 1, 1000, 1])
    res.createResource("copper_kelsh", "Copper", "Kelsh", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [507, 817, 444, 485, 817, 617, 617, 0, 0, 1, 0])
    max = array('h', [649, 906, 568, 565, 906, 706, 706, 1, 1, 1000, 1])
    res.createResource("copper_mythra", "Copper", "Mythra", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [596, 872, 521, 535, 872, 672, 672, 0, 0, 1, 0])
    max = array('h', [738, 961, 646, 615, 961, 761, 761, 1, 1, 1000, 1])
    res.createResource("copper_platinite", "Copper", "Platinite", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [685, 928, 599, 585, 928, 728, 728, 0, 0, 1, 0])
    max = array('h', [800, 1000, 700, 650, 1000, 800, 800, 1, 1, 1000, 1])
    res.createResource("copper_polysteel", "Copper", "Polysteel", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1000, 1000, 1000, 1000, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("copper_borocarbitic", "Copper", "Borocarbitic", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [1, 1, 300, 500, 1, 400, 400, 0, 0, 1, 0])
    max = array('h', [131, 82, 414, 581, 98, 498, 498, 1, 1, 1000, 1])
    res.createResource("iron_plumbum", "Iron", "Plumbum", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [71, 45, 361, 544, 53, 453, 453, 0, 0, 1, 0])
    max = array('h', [231, 144, 501, 644, 173, 573, 573, 1, 1, 1000, 1])
    res.createResource("iron_polonium", "Iron", "Polonium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [171, 107, 559, 606, 128, 528, 528, 0, 0, 1, 0])
    max = array('h', [331, 207, 589, 706, 238, 648, 648, 1, 1, 1000, 1])
    res.createResource("iron_axidite", "Iron", "Axidite", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [271, 169, 536, 669, 203, 603, 603, 0, 0, 1, 0])
    max = array('h', [430, 269, 676, 769, 323, 723, 723, 1, 1, 1000, 1])
    res.createResource("iron_bronzium", "Iron", "Bronzium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [371, 232, 624, 731, 278, 678, 678, 0, 0, 1, 0])
    max = array('h', [530, 332, 764, 831, 398, 798, 798, 1, 1, 1000, 1])
    res.createResource("iron_colat", "Iron", "Colat", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [470, 294, 711, 794, 353, 753, 753, 0, 0, 1, 0])
    max = array('h', [630, 394, 851, 894, 473, 873, 873, 1, 1, 1000, 1])
    res.createResource("iron_dolovite", "Iron", "Dolovite", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [570, 357, 799, 856, 428, 828, 828, 0, 0, 1, 0])
    max = array('h', [730, 456, 939, 956, 548, 948, 948, 1, 1, 1000, 1])
    res.createResource("iron_doonium", "Iron", "Doonium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [670, 419, 886, 919, 503, 903, 903, 0, 0, 1, 0])
    max = array('h', [800, 500, 1000, 1000, 600, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("iron_kammris", "Iron", "Kammris", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [1, 1, 500, 600, 1, 500, 400, 0, 0, 1, 0])
    max = array('h', [105, 85, 565, 652, 53, 565, 478, 1, 1, 1000, 1])
    res.createResource("steel_rhodium", "Steel", "Rhodium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [57, 46, 535, 628, 29, 535, 442, 0, 0, 1, 0])
    max = array('h', [185, 150, 615, 692, 93, 615, 538, 1, 1, 1000, 1])
    res.createResource("steel_kiirium", "Steel", "Kiirium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [137, 111, 585, 668, 69, 585, 502, 0, 0, 1, 0])
    max = array('h', [265, 215, 665, 732, 133, 665, 598, 1, 1, 1000, 1])
    res.createResource("steel_cubirian", "Steel", "Cubirian", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [217, 176, 635, 708, 109, 635, 562, 0, 0, 1, 0])
    max = array('h', [345, 280, 715, 772, 173, 715, 658, 1, 1, 1000, 1])
    res.createResource("steel_thoranium", "Steel", "Thoranium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [297, 241, 685, 748, 149, 685, 622, 0, 0, 1, 0])
    max = array('h', [424, 345, 765, 812, 212, 765, 718, 1, 1, 1000, 1])
    res.createResource("steel_neutronium", "Steel", "Neutronium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [377, 306, 735, 788, 189, 735, 682, 0, 0, 1, 0])
    max = array('h', [504, 410, 815, 852, 252, 815, 778, 1, 1, 1000, 1])
    res.createResource("steel_duranium", "Steel", "Duranium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [456, 371, 785, 828, 228, 785, 742, 0, 0, 1, 0])
    max = array('h', [584, 475, 865, 892, 292, 865, 838, 1, 1, 1000, 1])
    res.createResource("steel_ditanium", "Steel", "Ditanium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [536, 436, 835, 868, 268, 835, 802, 0, 0, 1, 0])
    max = array('h', [664, 540, 915, 932, 332, 915, 898, 1, 1, 1000, 1])
    res.createResource("steel_quadranium", "Steel", "Quadranium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [616, 501, 885, 908, 308, 885, 862, 0, 0, 1, 0])
    max = array('h', [744, 605, 965, 972, 372, 965, 958, 1, 1, 1000, 1])
    res.createResource("steel_carbonite", "Steel", "Carbonite", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [696, 566, 935, 948, 348, 935, 922, 0, 0, 1, 0])
    max = array('h', [800, 650, 1000, 1000, 400, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("steel_duralloy", "Steel", "Duralloy", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [300, 0, 1, 400, 300, 1, 200, 0, 0, 1, 0])
    max = array('h', [414, 1, 115, 498, 414, 115, 330, 1, 1, 1000, 1])
    res.createResource("ore_carbonate_alantium", "Carbonate Ore", "Alantium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [361, 0, 62, 453, 361, 62, 270, 0, 0, 1, 0])
    max = array('h', [501, 1, 202, 573, 501, 202, 430, 1, 1, 1000, 1])
    res.createResource("ore_carbonate_barthierium", "Carbonate Ore", "Barthierium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [449, 0, 150, 528, 449, 150, 370, 0, 0, 1, 0])
    max = array('h', [598, 1, 298, 648, 598, 289, 530, 1, 1, 1000, 1])
    res.createResource("ore_carbonate_chromite", "Carbonate Ore", "Chromite", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [536, 0, 237, 603, 536, 237, 470, 0, 0, 1, 0])
    max = array('h', [676, 1, 377, 723, 676, 377, 630, 1, 1, 1000, 1])
    res.createResource("ore_carbonate_frasium", "Carbonate Ore", "Frasium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [624, 0, 324, 678, 624, 324, 570, 0, 0, 1, 0])
    max = array('h', [764, 1, 464, 798, 764, 464, 730, 1, 1, 1000, 1])
    res.createResource("ore_carbonate_lommite", "Carbonate Ore", "Lommite", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [711, 0, 412, 753, 711, 412, 670, 0, 0, 1, 0])
    max = array('h', [851, 1, 551, 873, 851, 551, 830, 1, 1, 1000, 1])
    res.createResource("ore_carbonate_ostrine", "Carbonate Ore", "Ostrine", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [799, 0, 499, 828, 799, 499, 770, 0, 0, 1, 0])
    max = array('h', [939, 1, 639, 948, 939, 639, 930, 1, 1, 1000, 1])
    res.createResource("ore_carbonate_varium", "Carbonate Ore", "Varium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [886, 0, 586, 903, 886, 586, 870, 0, 0, 1, 0])
    max = array('h', [1000, 1, 700, 1000, 1000, 700, 1000, 1, 1, 1000, 1])
    res.createResource("ore_carbonate_zinsiam", "Carbonate Ore", "Zinsiam", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [300, 0, 1, 300, 300, 1, 1, 0, 0, 1, 0])
    max = array('h', [452, 1, 152, 452, 452, 131, 152, 1, 1, 1000, 1])
    res.createResource("ore_siliclastic_ardanium", "Siliclastic Ore", "Ardanium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [382, 0, 83, 382, 382, 71, 83, 0, 0, 1, 0])
    max = array('h', [568, 1, 269, 568, 568, 231, 269, 1, 1, 1000, 1])
    res.createResource("ore_siliclastic_cortosis", "Siliclastic Ore", "Cortosis", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [498, 0, 199, 498, 498, 171, 199, 0, 0, 1, 0])
    max = array('h', [685, 1, 385, 685, 685, 330, 385, 1, 1, 1000, 1])
    res.createResource("ore_siliclastic_crism", "Siliclastic Ore", "Crism", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [615, 0, 316, 615, 615, 271, 316, 0, 0, 1, 0])
    max = array('h', [802, 1, 502, 802, 802, 430, 502, 1, 1, 1000, 1])
    res.createResource("ore_siliclastic_malab", "Siliclastic Ore", "Malab", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [732, 0, 432, 732, 732, 370, 432, 0, 0, 1, 0])
    max = array('h', [918, 1, 618, 918, 918, 530, 618, 1, 1, 1000, 1])
    res.createResource("ore_siliclastic_robindun", "Siliclastic Ore", "Robindun", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [848, 0, 549, 848, 848, 470, 549, 0, 0, 1, 0])
    max = array('h', [1000, 1, 700, 1000, 1000, 600, 700, 1, 1, 1000, 1])
    res.createResource("ore_siliclastic_tertian", "Siliclastic Ore", "Tertian", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [200, 0, 300, 400, 1, 400, 400, 0, 0, 1, 0])
    max = array('h', [304, 1, 391, 478, 79, 478, 478, 1, 1, 1000, 1])
    res.createResource("ore_extrusive_bene", "Extrusive Ore", "Bene", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [256, 0, 349, 442, 43, 442, 442, 0, 0, 1, 0])
    max = array('h', [384, 1, 461, 538, 139, 538, 538, 1, 1, 1000, 1])
    res.createResource("ore_extrusive_chronamite", "Extrusive Ore", "Chronamite", 0, 0, min, max, 10000000, 10080000)
    

def generate2(core):
    res = core.resourceService
    
    min = array('h', [336, 0, 419, 502, 103, 502, 502, 0, 0, 1, 0])
    max = array('h', [464, 1, 531, 598, 199, 598, 598, 1, 1, 1000, 1])
    res.createResource("ore_extrusive_ilimium", "Extrusive Ore", "Ilimium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [416, 0, 489, 562, 163, 562, 562, 0, 0, 1, 0])
    max = array('h', [544, 1, 601, 658, 259, 658, 658, 1, 1, 1000, 1])
    res.createResource("ore_extrusive_kalonterium", "Extrusive Ore", "Kalonterium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [496, 0, 559, 622, 223, 622, 622, 0, 0, 1, 0])
    max = array('h', [624, 1, 671, 718, 318, 718, 718, 1, 1, 1000, 1])
    res.createResource("ore_extrusive_keschel", "Extrusive Ore", "Keschel", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [576, 0, 629, 682, 283, 682, 682, 0, 0, 1, 0])
    max = array('h', [704, 1, 741, 778, 378, 778, 778, 1, 1, 1000, 1])
    res.createResource("ore_extrusive_lidium", "Extrusive Ore", "Lidium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [656, 0, 699, 742, 342, 742, 742, 0, 0, 1, 0])
    max = array('h', [784, 1, 811, 838, 438, 838, 838, 1, 1, 1000, 1])
    res.createResource("ore_extrusive_maranium", "Extrusive Ore", "Maranium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [736, 0, 769, 802, 402, 802, 802, 0, 0, 1, 0])
    max = array('h', [864, 1, 881, 898, 498, 898, 898, 1, 1, 1000, 1])
    res.createResource("ore_extrusive_pholokite", "Extrusive Ore", "Pholokite", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [816, 0, 839, 862, 462, 862, 862, 0, 0, 1, 0])
    max = array('h', [944, 1, 951, 958, 558, 958, 958, 1, 1, 1000, 1])
    res.createResource("ore_extrusive_quadrenium", "Extrusive Ore", "Quadrenium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [896, 0, 909, 922, 522, 922, 922, 0, 0, 1, 0])
    max = array('h', [1000, 1, 1000, 1000, 600, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("ore_extrusive_vintrium", "Extrusive Ore", "Vintrium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [200, 0, 300, 700, 1, 500, 400, 0, 0, 1, 0])
    max = array('h', [316, 1, 401, 743, 88, 572, 487, 1, 1, 1000, 1])
    res.createResource("ore_intrusive_berubium", "Intrusive Ore", "Berubium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [262, 0, 354, 723, 48, 539, 447, 0, 0, 1, 0])
    max = array('h', [404, 1, 479, 777, 154, 628, 553, 1, 1, 1000, 1])
    res.createResource("ore_intrusive_chanlon", "Intrusive Ore", "Chanlon", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [351, 0, 432, 757, 114, 594, 513, 0, 0, 1, 0])
    max = array('h', [493, 1, 557, 810, 221, 683, 620, 1, 1, 1000, 1])
    res.createResource("ore_intrusive_corintium", "Intrusive Ore", "Corintium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [440, 0, 510, 790, 181, 650, 580, 0, 0, 1, 0])
    max = array('h', [582, 1, 634, 843, 287, 739, 687, 1, 1, 1000, 1])
    res.createResource("ore_intrusive_derillium", "Intrusive Ore", "Derillium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [529, 0, 588, 823, 247, 706, 647, 0, 0, 1, 0])
    max = array('h', [671, 1, 712, 877, 354, 794, 753, 1, 1, 1000, 1])
    res.createResource("ore_intrusive_oridium", "Intrusive Ore", "Oridium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [618, 0, 666, 857, 314, 761, 713, 0, 0, 1, 0])
    max = array('h', [760, 1, 790, 910, 420, 850, 820, 1, 1, 1000, 1])
    res.createResource("ore_intrusive_dylinium", "Intrusive Ore", "Dylinium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [707, 0, 743, 890, 380, 817, 780, 0, 0, 1, 0])
    max = array('h', [849, 1, 868, 943, 487, 906, 887, 1, 1, 1000, 1])
    res.createResource("ore_intrusive_hollinium", "Intrusive Ore", "Hollinium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [796, 0, 821, 923, 447, 872, 847, 0, 0, 1, 0])
    max = array('h', [938, 1, 946, 977, 553, 961, 953, 1, 1, 1000, 1])
    res.createResource("ore_intrusive_ionite", "Intrusive Ore", "Ionite", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [884, 0, 899, 957, 513, 928, 913, 0, 0, 1, 0])
    max = array('h', [1000, 1, 1000, 1000, 600, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("ore_intrusive_katrium", "Intrusive Ore", "Katrium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [1, 0, 1, 600, 1, 1, 1, 1, 0, 1, 0])
    max = array('h', [131, 1, 131, 652, 79, 131, 131, 105, 1, 1000, 1])
    res.createResource("armophous_bospridium", "Amorphous Gemstone", "Bospridium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [71, 0, 71, 628, 43, 71, 71, 57, 0, 1, 0])
    max = array('h', [231, 1, 231, 692, 139, 231, 231, 185, 1, 1000, 1])
    res.createResource("armophous_baradium", "Amorphous Gemstone", "Baradium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [171, 0, 171, 668, 103, 171, 171, 137, 0, 1, 0])
    max = array('h', [331, 1, 331, 732, 199, 331, 331, 265, 1, 1000, 1])
    res.createResource("armophous_regvis", "Amorphous Gemstone", "Regvis", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [271, 0, 271, 708, 163, 271, 271, 217, 0, 1, 0])
    max = array('h', [431, 1, 431, 772, 259, 431, 431, 345, 1, 1000, 1])
    res.createResource("armophous_plexite", "Amorphous Gemstone", "Plexite", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [371, 0, 371, 748, 223, 371, 371, 297, 0, 1, 0])
    max = array('h', [530, 1, 530, 812, 318, 530, 530, 424, 1, 1000, 1])
    res.createResource("armophous_rudic", "Amorphous Gemstone", "Rudic", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [471, 0, 471, 788, 283, 471, 471, 377, 0, 1, 0])
    max = array('h', [630, 1, 630, 852, 378, 630, 630, 504, 1, 1000, 1])
    res.createResource("armophous_ryll", "Amorphous Gemstone", "Ryll", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [570, 0, 570, 828, 342, 570, 570, 456, 0, 1, 0])
    max = array('h', [730, 1, 730, 892, 438, 730, 730, 584, 1, 1000, 1])
    res.createResource("armophous_sedrellium", "Amorphous Gemstone", "sedrellium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [670, 0, 670, 868, 402, 670, 670, 536, 0, 1, 0])
    max = array('h', [830, 1, 830, 932, 498, 830, 830, 664, 1, 1000, 1])
    res.createResource("armophous_stygium", "Amorphous Gemstone", "Stygium", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [770, 0, 770, 908, 462, 770, 770, 616, 0, 1, 0])
    max = array('h', [930, 1, 930, 972, 558, 930, 930, 744, 1, 1000, 1])
    res.createResource("armophous_vendusii", "Amorphous Gemstone", "Vendusii", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [870, 0, 870, 948, 522, 870, 870, 696, 0, 1, 0])
    max = array('h', [1000, 1, 1000, 1000, 600, 1000, 1000, 800, 1, 1000, 1])
    res.createResource("armophous_baltaran", "Amorphous Gemstone", "Baltaran", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [1, 0, 1, 700, 1, 300, 300, 500, 0, 1, 0])
    max = array('h', [163, 1, 163, 749, 66, 414, 414, 581, 1, 1000, 1])
    res.createResource("crystalline_byrothsis", "Crystalline Gemstone", "Byrothsis", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [88, 0, 88, 726, 36, 361, 361, 544, 0, 1, 0])
    max = array('h', [288, 1, 288, 786, 116, 501, 501, 644, 1, 1000, 1])
    res.createResource("crystalline_gallinorian", "Crystalline Gemstone", "Gallinorian", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [213, 0, 213, 764, 86, 449, 449, 606, 0, 1, 0])
    max = array('h', [413, 1, 413, 824, 166, 589, 589, 706, 1, 1000, 1])
    res.createResource("crystalline_green_diamond", "Crystalline Gemstone", "Green Diamond", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [713, 0, 713, 914, 285, 799, 799, 856, 0, 1, 0])
    max = array('h', [913, 1, 913, 974, 365, 939, 939, 956, 1, 1000, 1])
    res.createResource("crystalline_laboi_mineral_crystal", "Crystalline Gemstone", "Laboi Mineral Crystal", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [338, 0, 338, 801, 136, 536, 536, 669, 0, 1, 0])
    max = array('h', [538, 1, 538, 861, 215, 676, 676, 769, 1, 1000, 1])
    res.createResource("crystalline_kerol_firegem", "Crystalline Gemstone", "Kerol Fire-Gem", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [463, 0, 463, 839, 186, 624, 624, 731, 0, 1, 0])
    max = array('h', [663, 1, 663, 899, 265, 764, 764, 831, 1, 1000, 1])
    res.createResource("crystalline_seafah_jewel", "Crystalline Gemstone", "Seafah Jewel", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [588, 0, 588, 876, 235, 711, 711, 794, 0, 1, 0])
    max = array('h', [788, 1, 788, 936, 315, 851, 851, 894, 1, 1000, 1])
    res.createResource("crystalline_sormahil_firegem", "Crystalline Gemstone", "Sormahil Fire-Gem", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [713, 0, 713, 914, 285, 799, 799, 856, 0, 1, 0])
    max = array('h', [913, 1, 913, 974, 365, 939, 939, 956, 1, 1000, 1])
    res.createResource("crystalline_laboi_mineral", "Crystalline Gemstone", "Laboi mineral", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [838, 0, 838, 951, 335, 886, 886, 919, 0, 1, 0])
    max = array('h', [1000, 1, 1000, 1000, 400, 1000, 1000, 1000, 1, 1000, 1])
    res.createResource("crystalline_vertex", "Crystalline Gemstone", "Vertex", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 400, 0, 0, 0, 0, 0, 500, 1, 0])
    max = array('h', [1, 1, 474, 1, 1, 1, 1, 1, 593, 1000, 1])
    res.createResource("radioactive_type1", "Radioactive", "Class 1", 0, 25, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 400, 0, 0, 0, 0, 0, 550, 1, 0])
    max = array('h', [1, 1, 531, 1, 1, 1, 1, 1, 664, 1000, 1])
    res.createResource("radioactive_type2", "Radioactive", "Class 2", 0, 25, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 497, 0, 0, 0, 0, 0, 621, 1, 0])
    max = array('h', [1, 1, 589, 1, 1, 1, 1, 1, 736, 1000, 1])
    res.createResource("radioactive_type3", "Radioactive", "Class 3", 0, 25, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 554, 0, 0, 0, 0, 0, 693, 1, 0])
    max = array('h', [1, 1, 646, 1, 1, 1, 1, 1, 807, 1000, 1])
    res.createResource("radioactive_type4", "Radioactive", "Class 4", 0, 25, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 611, 0, 0, 0, 0, 0, 764, 1, 0])
    max = array('h', [1, 1, 703, 1, 1, 1, 1, 1, 879, 1000, 1])
    res.createResource("radioactive_type5", "Radioactive", "Class 5", 0, 25, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 669, 0, 0, 0, 0, 0, 836, 1, 0])
    max = array('h', [1, 1, 760, 1, 1, 1, 1, 1, 950, 1000, 1])
    res.createResource("radioactive_type6", "Radioactive", "Class 6", 0, 25, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 726, 0, 0, 0, 0, 0, 907, 1, 0])
    max = array('h', [1, 1, 800, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("radioactive_type7", "Radioactive", "Class 7", 0, 25, min, max, 10000000, 10080000)
    
    min = array('h', [1, 1, 300, 300, 1, 400, 400, 0, 0, 1, 0])
    max = array('h', [800, 650, 1000, 1000, 600, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("metal_ferrous_unknown", "Ferrous Metal", "Unknown", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [1, 300, 1, 200, 300, 300, 300, 0, 0, 1, 0])
    max = array('h', [800, 1000, 700, 800, 1000, 900, 1000, 1, 1, 1000, 1])
    res.createResource("metal_nonferrous_unknown", "Non-Ferrous", "Unknown", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [300, 0, 1, 300, 300, 1, 1, 0, 0, 1, 0])
    max = array('h', [1000, 1, 700, 1000, 1000, 700, 1000, 1, 1, 1000, 1])
    res.createResource("ore_sedimentary_unknown", "Sedimentary Ore", "Unknown", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [200, 0, 300, 400, 1, 400, 400, 0, 0, 1, 0])
    max = array('h', [1000, 1, 1000, 1000, 800, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("ore_igneous_unknown", "Igneous Ore", "Unknown", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [1, 0, 1, 600, 1, 1, 1, 1, 0, 1, 0])
    max = array('h', [1000, 1, 1000, 1000, 600, 1000, 1000, 1000, 1, 1000, 1])
    res.createResource("gemstone_unknown", "Gemstone", "Unknown", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0])
    max = array('h', [1, 1, 800, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("radioactive_unknown", "Radioactive", "Unknown", 0, 25, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 0])
    max = array('h', [1, 1, 149, 1, 1, 1, 1, 1, 430, 1000, 1])
    res.createResource("petrochem_fuel_solid_type1", "Petro Fuel", "Class 1 Solid", 1, 1, min, max, 10000000, 10080000)
    

def generate3(core):
    res = core.resourceService
    
    min = array('h', [0, 0, 81, 0, 0, 0, 0, 0, 370, 1, 0])
    max = array('h', [1, 1, 264, 1, 1, 1, 1, 1, 530, 1000, 1])
    res.createResource("petrochem_fuel_solid_type2", "Petro Fuel", "Class 2 Solid", 1, 1, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 195, 0, 0, 0, 0, 0, 470, 1, 0])
    max = array('h', [1, 1, 378, 1, 1, 1, 1, 1, 630, 1000, 1])
    res.createResource("petrochem_fuel_solid_type3", "Petro Fuel", "Class 3 Solid", 1, 1, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 309, 0, 0, 0, 0, 0, 570, 1, 0])
    max = array('h', [1, 1, 492, 1, 1, 1, 1, 1, 730, 1000, 1])
    res.createResource("petrochem_fuel_solid_type4", "Petro Fuel", "Class 4 Solid", 1, 1, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 423, 0, 0, 0, 0, 0, 670, 1, 0])
    max = array('h', [1, 1, 606, 1, 1, 1, 1, 1, 830, 1000, 1])
    res.createResource("petrochem_fuel_solid_type5", "Petro Fuel", "Class 5 Solid", 1, 1, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 537, 0, 0, 0, 0, 0, 770, 1, 0])
    max = array('h', [1, 1, 720, 1, 1, 1, 1, 1, 930, 1000, 1])
    res.createResource("petrochem_fuel_solid_type6", "Petro Fuel", "Class 6 Solid", 1, 1, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 652, 0, 0, 0, 0, 0, 870, 1, 0])
    max = array('h', [1, 1, 800, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("petrochem_fuel_solid_type7", "Petro Fuel", "Class 7 Solid", 1, 1, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("petrochem_fuel_solid_unknown", "Petro Fuel", "Uknown Solid", 1, 1, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0])
    max = array('h', [1, 1, 600, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("petrochem_fuel_liquid_type1", "Petro Fuel", "Class 1 Liquid", 1, 1, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0])
    max = array('h', [1, 1, 600, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("petrochem_fuel_liquid_type2", "Petro Fuel", "Class 2 Liquid", 1, 1, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0])
    max = array('h', [1, 1, 600, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("petrochem_fuel_liquid_type3", "Petro Fuel", "Class 3 Liquid", 1, 1, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0])
    max = array('h', [1, 1, 600, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("petrochem_fuel_liquid_type4", "Petro Fuel", "Class 4 Liquid", 1, 1, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0])
    max = array('h', [1, 1, 600, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("petrochem_fuel_liquid_type5", "Petro Fuel", "Class 5 Liquid", 1, 1, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0])
    max = array('h', [1, 1, 600, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("petrochem_fuel_liquid_type6", "Petro Fuel", "Class 6 Liquid", 1, 1, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0])
    max = array('h', [1, 1, 600, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("petrochem_fuel_liquid_type7", "Petro Fuel", "Class 7 Liquid", 1, 1, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("petrochem_fuel_liquid_unknown", "Petro Fuel", "Unknown Liquid", 1, 1, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("petrochem_inert_lubricating_oil", "Oil", "Lubricating", 1, 1, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("petrochem_inert_polymer", "Polymer", "Polymer", 1, 1, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0])
    max = array('h', [1, 1, 81, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("gas_inert_hydron3", "Inert Gas", "Hydron3", 3, 2, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 44, 0, 0, 0, 0, 0, 0, 1, 0])
    max = array('h', [1, 1, 142, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("gas_inert_malium", "Inert Gas", "Malium", 3, 2, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 105, 0, 0, 0, 0, 0, 0, 1, 0])
    max = array('h', [1, 1, 204, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("gas_inert_bilal", "Inert Gas", "Bilal", 3, 2, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 167, 0, 0, 0, 0, 0, 0, 1, 0])
    max = array('h', [1, 1, 265, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("gas_inert_corthel", "Inert Gas", "Corthel", 3, 2, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 228, 0, 0, 0, 0, 0, 0, 1, 0])
    max = array('h', [1, 1, 327, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("gas_inert_culsion", "Inert Gas", "Culsion", 3, 2, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 290, 0, 0, 0, 0, 0, 0, 1, 0])
    max = array('h', [1, 1, 388, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("gas_inert_dioxis", "Inert Gas", "Dioxis", 3, 2, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 351, 0, 0, 0, 0, 0, 0, 1, 0])
    max = array('h', [1, 1, 450, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("gas_inert_hurlothrombic", "Inert Gas", "Hurlothrombic", 3, 2, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 413, 0, 0, 0, 0, 0, 0, 1, 0])
    max = array('h', [1, 1, 511, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("gas_inert_kaylon", "Inert Gas", "Kaylon", 3, 2, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 474, 0, 0, 0, 0, 0, 0, 1, 0])
    max = array('h', [1, 1, 573, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("gas_inert_korfaise", "Inert Gas", "Korfaise", 3, 2, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 536, 0, 0, 0, 0, 0, 0, 1, 0])
    max = array('h', [1, 1, 634, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("gas_inert_methanagen", "Inert Gas", "Methanagen", 3, 2, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 597, 0, 0, 0, 0, 0, 0, 1, 0])
    max = array('h', [1, 1, 696, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("gas_inert_mirth", "Inert Gas", "Mirth", 3, 2, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 659, 0, 0, 0, 0, 0, 0, 1, 0])
    max = array('h', [1, 1, 757, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("gas_inert_obah", "Inert Gas", "Obah", 3, 2, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 720, 0, 0, 0, 0, 0, 0, 1, 0])
    max = array('h', [1, 1, 800, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("gas_inert_rethin", "Inert Gas", "Rethin", 3, 2, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("gas_inert_unknown", "Inert Gas", "Unknown", 3, 2, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0])
    max = array('h', [1, 1, 400, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("gas_reactive_eleton", "Reactive Gas", "Eleton", 3, 2, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 100, 0, 0, 0, 0, 0, 0, 1, 0])
    max = array('h', [1, 1, 600, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("gas_reactive_irolunn", "Reactive Gas", "Irolunn", 3, 2, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 200, 0, 0, 0, 0, 0, 0, 1, 0])
    max = array('h', [1, 1, 700, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("gas_reactive_methane", "Reactive Gas", "Methane", 3, 2, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 300, 0, 0, 0, 0, 0, 0, 1, 0])
    max = array('h', [1, 1, 800, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("gas_reactive_orveth", "Reactive Gas", "Orveth", 3, 2, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 300, 0, 0, 0, 0, 0, 0, 1, 0])
    max = array('h', [1, 1, 900, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("gas_reactive_sig", "Reactive Gas", "Sig", 3, 2, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 400, 0, 0, 0, 0, 0, 0, 1, 0])
    max = array('h', [1, 1, 900, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("gas_reactive_skevon", "Reactive Gas", "Skevon", 3, 2, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 500, 0, 0, 0, 0, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("gas_reactive_tolium", "Reactive Gas", "Tolium", 3, 2, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("gas_reactive_unknown", "Reactive Gas", "Unknown", 3, 2, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 0, 600, 0])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("water_vapor_corellia", "Water Vapor", "Corellian", 3, 4, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 0, 600, 0])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("water_vapor_dantooine", "Water Vapor", "Dantooine", 4, 4, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 0, 600, 0])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("water_vapor_dathomir", "Water Vapor", "Dathomir", 4, 4, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 0, 600, 0])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("water_vapor_endor", "Water Vapor", "Endorian", 4, 4, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 0, 600, 0])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("water_vapor_lok", "Water Vapor", "Lokian", 4, 4, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 0, 600, 0])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("water_vapor_naboo", "Water Vapor", "Nabooian", 4, 4, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 0, 600, 0])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("water_vapor_rori", "Water Vapor", "Rori", 4, 4, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 0, 600, 0])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("water_vapor_talus", "Water Vapor", "Talusian", 4, 4, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 0, 600, 0])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("water_vapor_tatooine", "Water Vapor", "Tatooinian", 4, 4, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 0, 600, 0])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("water_vapor_yavin4", "Water Vapor", "Yavinian", 4, 4, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 1, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 400, 500, 1])
    res.createResource("energy_renewable_unlimited_wind_corellia", "Wind Renewable Energy", "Corellian", 6, 24, min, max, 10000000, 10080000)
    

def generate4(core):
    res = core.resourceService
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 1, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 400, 500, 1])
    res.createResource("energy_renewable_unlimited_wind_dantooine", "Wind Renewable Energy", "Dantooine", 6, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 1, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 400, 500, 1])
    res.createResource("energy_renewable_unlimited_wind_dathomir", "Wind Renewable Energy", "Dathomir", 6, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 1, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 400, 500, 1])
    res.createResource("energy_renewable_unlimited_wind_endor", "Wind Renewable Energy", "Endorian", 6, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 1, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 400, 500, 1])
    res.createResource("energy_renewable_unlimited_wind_lok", "Wind Renewable Energy", "Lokian", 6, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 1, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 400, 500, 1])
    res.createResource("energy_renewable_unlimited_wind_naboo", "Wind Renewable Energy", "Nabooian", 6, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 1, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 400, 500, 1])
    res.createResource("energy_renewable_unlimited_wind_rori", "Wind Renewable Energy", "Rori", 6, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 1, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 400, 500, 1])
    res.createResource("energy_renewable_unlimited_wind_talus", "Wind Renewable Energy", "Talusian", 6, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 1, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 400, 500, 1])
    res.createResource("energy_renewable_unlimited_wind_tatooine", "Wind Renewable Energy", "Tatooinian", 6, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 1, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 400, 500, 1])
    res.createResource("energy_renewable_unlimited_wind_yavin4", "Wind Renewable Energy", "Yavinian", 6, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 600, 500, 1])
    res.createResource("energy_renewable_unlimited_solar_corellia", "Solar Renewable Energy", "Corellian", 5, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 600, 500, 1])
    res.createResource("energy_renewable_unlimited_solar_dantooine", "Solar Renewable Energy", "Dantoine", 5, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 600, 500, 1])
    res.createResource("energy_renewable_unlimited_solar_dathomir", "Solar Renewable Energy", "Dathomirian", 5, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 600, 500, 1])
    res.createResource("energy_renewable_unlimited_solar_endor", "Solar Renewable Energy", "Endorian", 5, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 600, 500, 1])
    res.createResource("energy_renewable_unlimited_solar_lok", "Solar Renewable Energy", "Lokian", 5, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 600, 500, 1])
    res.createResource("energy_renewable_unlimited_solar_naboo", "Solar Renewable Energy", "Nabooian", 5, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 600, 500, 1])
    res.createResource("energy_renewable_unlimited_solar_rori", "Solar Renewable Energy", "Rori", 5, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 600, 500, 1])
    res.createResource("energy_renewable_unlimited_solar_talus", "Solar Renewable Energy", "Talusian", 5, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 600, 500, 1])
    res.createResource("energy_renewable_unlimited_solar_tatooine", "Solar Renewable Energy", "Tatooinian", 5, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 600, 500, 1])
    res.createResource("energy_renewable_unlimited_solar_yavin4", "Solar Renewable Energy", "Yavinian", 5, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_tidal_corellia", "Tidal Renewable Energy", "Corellian", 8, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_tidal_dantooine", "Tidal Renewable Energy", "Dantoine", 8, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_tidal_dathomir", "Tidal Renewable Energy", "Dathomirian", 8, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_tidal_endor", "Tidal Renewable Energy", "Endorian", 8, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_tidal_lok", "Tidal Renewable Energy", "Lokian", 8, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_tidal_naboo", "Tidal Renewable Energy", "Nabooian", 8, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_tidal_rori", "Tidal Renewable Energy", "Rori", 8, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_tidal_talus", "Tidal Renewable Energy", "Talusian", 8, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_tidal_tatooine", "Tidal Renewable Energy", "Tatooinian", 8, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_tidal_yavin4", "Tidal Renewable Energy", "Yavinian", 8, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_hydron3_corellia", "Hydron-3 Renewable Energy", "Corellian", 9, 23, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_hydron3_dantooine", "Hydron-3 Renewable Energy", "Dantoine", 9, 23, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_hydron3_dathomir", "Hydron-3 Renewable Energy", "Dathomirian", 9, 23, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_hydron3_endor", "Hydron-3 Renewable Energy", "Endorian", 9, 23, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_hydron3_lok", "Hydron-3 Renewable Energy", "Lokian", 9, 23, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_hydron3_naboo", "Hydron-3 Renewable Energy", "Nabooian", 9, 23, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_hydron3_rori", "Hydron-3 Renewable Energy", "Rori", 9, 23, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_hydron3_talus", "Hydron-3 Renewable Energy", "Talusian", 9, 23, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_hydron3_tatooine", "Hydron-3 Renewable Energy", "Tatooinian", 9, 23, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_hydron3_yavin4", "Hydron-3 Renewable Energy", "Yavinian", 9, 23, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_geothermal_corellia", "Geothermal Renewable Energy", "Corellian", 7, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_geothermal_dantooine", "Geothermal Renewable Energy", "Dantoine", 7, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_geothermal_dathomir", "Geothermal Renewable Energy", "Dathomirian", 7, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_geothermal_endor", "Geothermal Renewable Energy", "Endorian", 7, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_geothermal_lok", "Geothermal Renewable Energy", "Lokian", 7, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_geothermal_naboo", "Geothermal Renewable Energy", "Nabooian", 7, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_geothermal_rori", "Geothermal Renewable Energy", "Rori", 7, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_geothermal_talus", "Geothermal Renewable Energy", "Talusian", 7, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_geothermal_tatooine", "Geothermal Renewable Energy", "Tatooinian", 7, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_geothermal_yavin4", "Geothermal Renewable Energy", "Yavinian", 7, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 600, 100, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 600, 1, 1000, 400, 300, 1, 1, 1000, 1])
    res.createResource("softwood_conifer_corellia", "Conifer Wood", "Corellian", 2, 10, min, max, 10000000, 10080000)
    

def generate5(core):
    res = core.resourceService
    
    min = array('h', [0, 0, 1, 0, 600, 100, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 600, 1, 1000, 400, 300, 1, 1, 1000, 1])
    res.createResource("softwood_conifer_dantooine", "Conifer Wood", "Dantooine", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 600, 100, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 600, 1, 1000, 400, 300, 1, 1, 1000, 1])
    res.createResource("softwood_conifer_dathomir", "Conifer Wood", "Dathomirian", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 600, 100, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 600, 1, 1000, 400, 300, 1, 1, 1000, 1])
    res.createResource("softwood_conifer_endor", "Conifer Wood", "Endorian", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 600, 100, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 600, 1, 1000, 400, 300, 1, 1, 1000, 1])
    res.createResource("softwood_conifer_lok", "Conifer Wood", "Lokian", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 600, 100, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 600, 1, 1000, 400, 300, 1, 1, 1000, 1])
    res.createResource("softwood_conifer_naboo", "Conifer Wood", "Nabooian", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 600, 100, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 600, 1, 1000, 400, 300, 1, 1, 1000, 1])
    res.createResource("softwood_conifer_rori", "Conifer Wood", "Rori", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 600, 100, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 600, 1, 1000, 400, 300, 1, 1, 1000, 1])
    res.createResource("softwood_conifer_talus", "Conifer Wood", "Talusian", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 600, 100, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 600, 1, 1000, 400, 300, 1, 1, 1000, 1])
    res.createResource("softwood_conifer_tatooine", "Conifer Wood", "Tatooinian", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 600, 100, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 600, 1, 1000, 400, 300, 1, 1, 1000, 1])
    res.createResource("softwood_conifer_yavin4", "Conifer Wood", "Yavinian", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 800, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 500, 1, 1000, 400, 300, 1, 1, 1000, 1])
    res.createResource("softwood_evergreen_corellia", "Evergreen Wood", "Corellian", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 800, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 500, 1, 1000, 400, 300, 1, 1, 1000, 1])
    res.createResource("softwood_evergreen_dantooine", "Evergreen Wood", "Dantooine", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 800, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 500, 1, 1000, 400, 300, 1, 1, 1000, 1])
    res.createResource("softwood_evergreen_dathomir", "Evergreen Wood", "Dathomirian", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 800, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 500, 1, 1000, 400, 300, 1, 1, 1000, 1])
    res.createResource("softwood_evergreen_endor", "Evergreen Wood", "Endorian", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 800, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 500, 1, 1000, 400, 300, 1, 1, 1000, 1])
    res.createResource("softwood_evergreen_lok", "Evergreen Wood", "Lokian", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 800, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 500, 1, 1000, 400, 300, 1, 1, 1000, 1])
    res.createResource("softwood_evergreen_naboo", "Evergreen Wood", "Nabooian", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 800, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 500, 1, 1000, 400, 300, 1, 1, 1000, 1])
    res.createResource("softwood_evergreen_rori", "Evergreen Wood", "Rori", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 800, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 500, 1, 1000, 400, 300, 1, 1, 1000, 1])
    res.createResource("softwood_evergreen_talus", "Evergreen Wood", "Talusian", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 800, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 500, 1, 1000, 400, 300, 1, 1, 1000, 1])
    res.createResource("softwood_evergreen_tatooine", "Evergreen Wood", "Tatooinian", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 800, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 500, 1, 1000, 400, 300, 1, 1, 1000, 1])
    res.createResource("softwood_evergreen_yavin4", "Evergreen Wood", "Yavinian", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 400, 300, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 800, 1, 1000, 400, 800, 1, 1, 1000, 1])
    res.createResource("wood_deciduous_corellia", "Deciduous Wood", "Corellian", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 400, 300, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 800, 1, 1000, 400, 800, 1, 1, 1000, 1])
    res.createResource("wood_deciduous_dantooine", "Deciduous Wood", "Dantooine", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 400, 300, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 800, 1, 1000, 400, 800, 1, 1, 1000, 1])
    res.createResource("wood_deciduous_dathomir", "Deciduous Wood", "Dathomirian", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 400, 300, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 800, 1, 1000, 400, 800, 1, 1, 1000, 1])
    res.createResource("wood_deciduous_endor", "Deciduous Wood", "Endorian", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 400, 300, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 800, 1, 1000, 400, 800, 1, 1, 1000, 1])
    res.createResource("wood_deciduous_lok", "Deciduous Wood", "Lokian", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 400, 300, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 800, 1, 1000, 400, 800, 1, 1, 1000, 1])
    res.createResource("wood_deciduous_naboo", "Deciduous Wood", "Nabooian", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 400, 300, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 800, 1, 1000, 400, 800, 1, 1, 1000, 1])
    res.createResource("wood_deciduous_rori", "Deciduous Wood", "Rori", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 400, 300, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 800, 1, 1000, 400, 800, 1, 1, 1000, 1])
    res.createResource("wood_deciduous_talus", "Deciduous Wood", "Talusian", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 400, 300, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 800, 1, 1000, 400, 800, 1, 1, 1000, 1])
    res.createResource("wood_deciduous_tatooine", "Deciduous Wood", "Tatooinian", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 400, 300, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 800, 1, 1000, 400, 800, 1, 1, 1000, 1])
    res.createResource("wood_deciduous_yavin4", "Deciduous Wood", "Yavinian", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("fruit_fruits_corellia", "Fruit", "Corellian", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("fruit_fruits_dantooine", "Fruit", "Dantooine", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("fruit_fruits_dathomir", "Fruit", "Dathomirian", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("fruit_fruits_endor", "Fruit", "Endorian", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("fruit_fruits_lok", "Fruit", "Lokian", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("fruit_fruits_naboo", "Fruit", "Nabooian", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("fruit_fruits_rori", "Fruit", "Rori", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("fruit_fruits_talus", "Fruit", "Talusian", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("fruit_fruits_tatooine", "Fruit", "Tatooinian", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("fruit_fruits_yavin4", "Fruit", "Yavinian", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("fruit_flowers_corellia", "Flower Fruit", "Corellian", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("fruit_flowers_dantooine", "Flower Fruit", "Dantooine", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("fruit_flowers_dathomir", "Flower Fruit", "Dathomirian", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("fruit_flowers_endor", "Flower Fruit", "Endorian", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("fruit_flowers_lok", "Flower Fruit", "Lokian", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("fruit_flowers_naboo", "Flower Fruit", "Nabooian", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("fruit_flowers_rori", "Flower Fruit", "Rori", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("fruit_flowers_talus", "Flower Fruit", "Talusian", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("fruit_flowers_tatooine", "Flower Fruit", "Tatooinian", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("fruit_flowers_yavin4", "Flower Fruit", "Yavinian", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("corn_domesticated_corellia", "Domesticated Corn", "Corellian", 2, 9, min, max, 10000000, 10080000)
    

def generate6(core):
    res = core.resourceService
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("corn_domesticated_dantooine", "Domesticated Corn", "Dantooine", 2, 9, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("corn_domesticated_dathomir", "Domesticated Corn", "Dathomirian", 2, 9, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("corn_domesticated_endor", "Domesticated Corn", "Endorian", 2, 9, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("corn_domesticated_lok", "Domesticated Corn", "Lokian", 2, 9, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("corn_domesticated_naboo", "Domesticated Corn", "Nabooian", 2, 9, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("corn_domesticated_rori", "Domesticated Corn", "Rori", 2, 9, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("corn_domesticated_talus", "Domesticated Corn", "Talusian", 2, 9, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("corn_domesticated_tatooine", "Domesticated Corn", "Tatooinian", 2, 9, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("corn_domesticated_yavin4", "Domesticated Corn", "Yavinian", 2, 9, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("oats_domesticated_corellia", "Domesticated Oats", "Corellian", 2, 16, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("oats_domesticated_dantooine", "Domesticated Oats", "Dantooine", 2, 16, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("oats_domesticated_dathomir", "Domesticated Oats", "Dathomirian", 2, 16, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("oats_domesticated_endor", "Domesticated Oats", "Endorian", 2, 16, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("oats_domesticated_lok", "Domesticated Oats", "Lokian", 2, 16, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("oats_domesticated_naboo", "Domesticated Oats", "Nabooian", 2, 16, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("oats_domesticated_rori", "Domesticated Oats", "Rori", 2, 16, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("oats_domesticated_talus", "Domesticated Oats", "Talusian", 2, 16, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("oats_domesticated_tatooine", "Domesticated Oats", "Tatooinian", 2, 16, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("oats_domesticated_yavin4", "Domesticated Oats", "Yavinian", 2, 16, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("rice_domesticated_corellia", "Domesticated Rice", "Corellian", 2, 17, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("rice_domesticated_dantooine", "Domesticated Rice", "Dantooine", 2, 17, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("rice_domesticated_dathomir", "Domesticated Rice", "Dathomirian", 2, 17, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("rice_domesticated_endor", "Domesticated Rice", "Endorian", 2, 17, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("rice_domesticated_lok", "Domesticated Rice", "Lokian", 2, 17, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("rice_domesticated_naboo", "Domesticated Rice", "Nabooian", 2, 17, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("rice_domesticated_rori", "Domesticated Rice", "Rori", 2, 17, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("rice_domesticated_talus", "Domesticated Rice", "Talusian", 2, 17, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("rice_domesticated_tatooine", "Domesticated Rice", "Tatooinian", 2, 17, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("rice_domesticated_yavin4", "Domesticated Rice", "Yavinian", 2, 17, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("wheat_domesticated_corellia", "Domesticated Wheat", "Corellian", 2, 21, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("wheat_domesticated_dantooine", "Domesticated Wheat", "Dantooine", 2, 21, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("wheat_domesticated_dathomir", "Domesticated Wheat", "Dathomirian", 2, 21, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("wheat_domesticated_endor", "Domesticated Wheat", "Endorian", 2, 21, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("wheat_domesticated_lok", "Domesticated Wheat", "Lokian", 2, 21, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("wheat_domesticated_naboo", "Domesticated Wheat", "Nabooian", 2, 21, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("wheat_domesticated_rori", "Domesticated Wheat", "Rori", 2, 21, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("wheat_domesticated_talus", "Domesticated Wheat", "Talusian", 2, 21, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("wheat_domesticated_tatooine", "Domesticated Wheat", "Tatooinian", 2, 21, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("wheat_domesticated_yavin4", "Domesticated Wheat", "Yavinian", 2, 21, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("corn_wild_corellia", "Wild Corn", "Corellian", 2, 9, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("corn_wild_dantooine", "Wild Corn", "Dantooine", 2, 9, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("corn_wild_dathomir", "Wild Corn", "Dathomirian", 2, 9, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("corn_wild_endor", "Wild Corn", "Endorian", 2, 9, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("corn_wild_lok", "Wild Corn", "Lokian", 2, 9, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("corn_wild_naboo", "Wild Corn", "Nabooian", 2, 9, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("corn_wild_rori", "Wild Corn", "Rori", 2, 9, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("corn_wild_talus", "Wild Corn", "Talusian", 2, 9, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("corn_wild_tatooine", "Wild Corn", "Tatooinian", 2, 9, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("corn_wild_yavin4", "Wild Corn", "Yavinian", 2, 9, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("oats_wild_corellia", "Wild Oats", "Corellian", 2, 16, min, max, 10000000, 10080000)
    

def generate7(core):
    res = core.resourceService
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("oats_wild_dantooine", "Wild Oats", "Dantooine", 2, 16, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("oats_wild_dathomir", "Wild Oats", "Dathomirian", 2, 16, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("oats_wild_endor", "Wild Oats", "Endorian", 2, 16, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("oats_wild_lok", "Wild Oats", "Lokian", 2, 16, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("oats_wild_naboo", "Wild Oats", "Nabooian", 2, 16, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("oats_wild_rori", "Wild Oats", "Rori", 2, 16, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("oats_wild_talus", "Wild Oats", "Talusian", 2, 16, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("oats_wild_tatooine", "Wild Oats", "Tatooinian", 2, 16, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("oats_wild_yavin4", "Wild Oats", "Yavinian", 2, 16, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("rice_wild_corellia", "Wild Rice", "Corellian", 2, 17, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("rice_wild_dantooine", "Wild Rice", "Dantooine", 2, 17, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("rice_wild_dathomir", "Wild Rice", "Dathomirian", 2, 17, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("rice_wild_endor", "Wild Rice", "Endorian", 2, 17, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("rice_wild_lok", "Wild Rice", "Lokian", 2, 17, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("rice_wild_naboo", "Wild Rice", "Nabooian", 2, 17, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("rice_wild_rori", "Wild Rice", "Rori", 2, 17, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("rice_wild_talus", "Wild Rice", "Talusian", 2, 17, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("rice_wild_tatooine", "Wild Rice", "Tatooinian", 2, 17, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("rice_wild_yavin4", "Wild Rice", "Yavinian", 2, 17, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("wheat_wild_corellia", "Wild Wheat", "Corellian", 2, 21, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("wheat_wild_dantooine", "Wild Wheat", "Dantooine", 2, 21, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("wheat_wild_dathomir", "Wild Wheat", "Dathomirian", 2, 21, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("wheat_wild_endor", "Wild Wheat", "Endorian", 2, 21, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("wheat_wild_lok", "Wild Wheat", "Lokian", 2, 21, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("wheat_wild_naboo", "Wild Wheat", "Nabooian", 2, 21, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("wheat_wild_rori", "Wild Wheat", "Rori", 2, 21, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("wheat_wild_talus", "Wild Wheat", "Talusian", 2, 21, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("wheat_wild_tatooine", "Wild Wheat", "Tatooinian", 2, 21, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("wheat_wild_yavin4", "Wild Wheat", "Yavinian", 2, 21, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_beans_corellia", "Vegetable Beans", "Corellian", 2, 6, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_beans_dantooine", "Vegetable Beans", "Dantooine", 2, 6, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_beans_dathomir", "Vegetable Beans", "Dathomirian", 2, 6, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_beans_endor", "Vegetable Beans", "Endorian", 2, 6, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_beans_lok", "Vegetable Beans", "Lokian", 2, 6, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_beans_naboo", "Vegetable Beans", "Nabooian", 2, 6, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_beans_rori", "Vegetable Beans", "Rori", 2, 6, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_beans_talus", "Vegetable Beans", "Talusian", 2, 6, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_beans_tatooine", "Vegetable Beans", "Tatooinian", 2, 6, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_beans_yavin4", "Vegetable Beans", "Yavinian", 2, 6, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("fruit_berries_corellia", "Berry Fruit", "Corellian", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("fruit_berries_dantooine", "Berry Fruit", "Dantooine", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("fruit_berries_dathomir", "Berry Fruit", "Dathomirian", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("fruit_berries_endor", "Berry Fruit", "Endorian", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("fruit_berries_lok", "Berry Fruit", "Lokian", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("fruit_berries_naboo", "Berry Fruit", "Nabooian", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("fruit_berries_rori", "Berry Fruit", "Rori", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("fruit_berries_talus", "Berry Fruit", "Talusian", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("fruit_berries_tatooine", "Berry Fruit", "Tatooinian", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("fruit_berries_yavin4", "Berry Fruit", "Yavinian", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_fungi_corellia", "Vegetable Fungus", "Corellian", 2, 12, min, max, 10000000, 10080000)
    

def generate8(core):
    res = core.resourceService
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_fungi_dantooine", "Vegetable Fungus", "Dantooine", 2, 12, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_fungi_dathomir", "Vegetable Fungus", "Dathomirian", 2, 12, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_fungi_endor", "Vegetable Fungus", "Endorian", 2, 12, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_fungi_lok", "Vegetable Fungus", "Lokian", 2, 12, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_fungi_naboo", "Vegetable Fungus", "Nabooian", 2, 12, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_fungi_rori", "Vegetable Fungus", "Rori", 2, 12, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_fungi_talus", "Vegetable Fungus", "Talusian", 2, 12, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_fungi_tatooine", "Vegetable Fungus", "Tatooinian", 2, 12, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_fungi_yavin4", "Vegetable Fungus", "Yavinian", 2, 12, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_greens_corellia", "Vegetable Greens", "Corellian", 2, 13, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_greens_dantooine", "Vegetable Greens", "Dantooine", 2, 13, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_greens_dathomir", "Vegetable Greens", "Dathomirian", 2, 13, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_greens_endor", "Vegetable Greens", "Endorian", 2, 13, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_greens_lok", "Vegetable Greens", "Lokian", 2, 13, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_greens_naboo", "Vegetable Greens", "Nabooian", 2, 13, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_greens_rori", "Vegetable Greens", "Rori", 2, 13, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_greens_talus", "Vegetable Greens", "Talusian", 2, 13, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_greens_tatooine", "Vegetable Greens", "Tatooinian", 2, 13, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_greens_yavin4", "Vegetable Greens", "Yavinian", 2, 13, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_tubers_corellia", "Vegetable Tubers", "Corellian", 2, 20, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_tubers_dantooine", "Vegetable Tubers", "Dantooine", 2, 20, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_tubers_dathomir", "Vegetable Tubers", "Dathomirian", 2, 20, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_tubers_endor", "Vegetable Tubers", "Endorian", 2, 20, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_tubers_lok", "Vegetable Tubers", "Lokian", 2, 20, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_tubers_naboo", "Vegetable Tubers", "Nabooian", 2, 20, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_tubers_rori", "Vegetable Tubers", "Rori", 2, 20, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_tubers_talus", "Vegetable Tubers", "Talusian", 2, 20, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_tubers_tatooine", "Vegetable Tubers", "Tatooinian", 2, 20, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_tubers_yavin4", "Vegetable Tubers", "Yavinian", 2, 20, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 300, 0, 1, 400, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 500, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("bone_mammal_corellia", "Animal Bones", "Corellian", 10, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 300, 0, 1, 400, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 500, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("bone_mammal_dantooine", "Animal Bones", "Dantooine", 10, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 300, 0, 1, 400, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 500, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("bone_mammal_dathomir", "Animal Bones", "Dathomirian", 10, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 300, 0, 1, 400, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 500, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("bone_mammal_endor", "Animal Bones", "Endorian", 10, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 300, 0, 1, 400, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 500, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("bone_mammal_lok", "Animal Bones", "Lokian", 10, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 300, 0, 1, 400, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 500, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("bone_mammal_naboo", "Animal Bones", "Nabooian", 10, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 300, 0, 1, 400, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 500, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("bone_mammal_rori", "Animal Bones", "Rori", 10, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 300, 0, 1, 400, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 500, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("bone_mammal_talus", "Animal Bones", "Talusian", 10, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 300, 0, 1, 400, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 500, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("bone_mammal_tatooine", "Animal Bones", "Tatooinian", 10, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 300, 0, 1, 400, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 500, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("bone_mammal_yavin4", "Animal Bones", "Yavinian", 10, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 100, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 700, 1, 600, 500, 500, 1, 1, 1000, 1])
    res.createResource("bone_avian_corellia", "Avian Bones", "Corellian", 10, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 100, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 700, 1, 600, 500, 500, 1, 1, 1000, 1])
    res.createResource("bone_avian_dantooine", "Avian Bones", "Dantooine", 10, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 100, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 700, 1, 600, 500, 500, 1, 1, 1000, 1])
    res.createResource("bone_avian_dathomir", "Avian Bones", "Dathomirian", 10, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 100, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 700, 1, 600, 500, 500, 1, 1, 1000, 1])
    res.createResource("bone_avian_endor", "Avian Bones", "Endorian", 10, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 100, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 700, 1, 600, 500, 500, 1, 1, 1000, 1])
    res.createResource("bone_avian_lok", "Avian Bones", "Lokian", 10, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 100, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 700, 1, 600, 500, 500, 1, 1, 1000, 1])
    res.createResource("bone_avian_naboo", "Avian Bones", "Nabooian", 10, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 100, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 700, 1, 600, 500, 500, 1, 1, 1000, 1])
    res.createResource("bone_avian_rori", "Avian Bones", "Rori", 10, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 100, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 700, 1, 600, 500, 500, 1, 1, 1000, 1])
    res.createResource("bone_avian_talus", "Avian Bones", "Talusian", 10, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 100, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 700, 1, 600, 500, 500, 1, 1, 1000, 1])
    res.createResource("bone_avian_tatooine", "Avian Bones", "Tatooinian", 10, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 100, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 700, 1, 600, 500, 500, 1, 1, 1000, 1])
    res.createResource("bone_avian_yavin4", "Avian Bones", "Yavinian", 10, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 200, 0, 1, 300, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 500, 700, 500, 1, 1, 1000, 1])
    res.createResource("bone_horn_corellia", "Horn", "Corellian", 11, 19, min, max, 10000000, 10080000)
    

def generate9(core):
    res = core.resourceService
    
    min = array('h', [0, 0, 200, 0, 1, 300, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 500, 700, 500, 1, 1, 1000, 1])
    res.createResource("bone_horn_dantooine", "Horn", "Dantooine", 11, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 200, 0, 1, 300, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 500, 700, 500, 1, 1, 1000, 1])
    res.createResource("bone_horn_dathomir", "Horn", "Dathomirian", 11, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 200, 0, 1, 300, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 500, 700, 500, 1, 1, 1000, 1])
    res.createResource("bone_horn_endor", "Horn", "Endorian", 11, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 200, 0, 1, 300, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 500, 700, 500, 1, 1, 1000, 1])
    res.createResource("bone_horn_lok", "Horn", "Lokian", 11, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 200, 0, 1, 300, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 500, 700, 500, 1, 1, 1000, 1])
    res.createResource("bone_horn_naboo", "Horn", "Nabooian", 11, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 200, 0, 1, 300, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 500, 700, 500, 1, 1, 1000, 1])
    res.createResource("bone_horn_rori", "Horn", "Rori", 11, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 200, 0, 1, 300, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 500, 700, 500, 1, 1, 1000, 1])
    res.createResource("bone_horn_talus", "Horn", "Talusian", 11, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 200, 0, 1, 300, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 500, 700, 500, 1, 1, 1000, 1])
    res.createResource("bone_horn_tatooine", "Horn", "Tatooinian", 11, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 200, 0, 1, 300, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 500, 700, 500, 1, 1, 1000, 1])
    res.createResource("bone_horn_yavin4", "Horn", "Yavinian", 11, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_bristley_corellia", "Bristley Hide", "Corellian", 12, 8, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_bristley_dantooine", "Bristley Hide", "Dantooine", 12, 8, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_bristley_dathomir", "Bristley Hide", "Dathomirian", 12, 8, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_bristley_endor", "Bristley Hide", "Endorian", 12, 8, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_bristley_lok", "Bristley Hide", "Lokian", 12, 8, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_bristley_naboo", "Bristley Hide", "Nabooian", 12, 8, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_bristley_rori", "Bristley Hide", "Rori", 12, 8, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_bristley_talus", "Bristley Hide", "Talusian", 12, 8, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_bristley_tatooine", "Bristley Hide", "Tatooinian", 12, 8, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_bristley_yavin4", "Bristley Hide", "Yavinian", 12, 8, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_leathery_corellia", "Leathery Hide", "Corellian", 12, 15, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_leathery_dantooine", "Leathery Hide", "Dantooine", 12, 15, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_leathery_dathomir", "Leathery Hide", "Dathomirian", 12, 15, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_leathery_endor", "Leathery Hide", "Endorian", 12, 15, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_leathery_lok", "Leathery Hide", "Lokian", 12, 15, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_leathery_naboo", "Leathery Hide", "Nabooian", 12, 15, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_leathery_rori", "Leathery Hide", "Rori", 12, 15, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_leathery_talus", "Leathery Hide", "Talusian", 12, 15, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_leathery_tatooine", "Leathery Hide", "Tatooinian", 12, 15, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_leathery_yavin4", "Leathery Hide", "Yavinian", 12, 15, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_scaley_corellia", "Scaley Hide", "Corellian", 12, 18, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_scaley_dantooine", "Scaley Hide", "Dantooine", 12, 18, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_scaley_dathomir", "Scaley Hide", "Dathomirian", 12, 18, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_scaley_endor", "Scaley Hide", "Endorian", 12, 18, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_scaley_lok", "Scaley Hide", "Lokian", 12, 18, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_scaley_naboo", "Scaley Hide", "Nabooian", 12, 18, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_scaley_rori", "Scaley Hide", "Rori", 12, 18, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_scaley_talus", "Scaley Hide", "Talusian", 12, 18, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_scaley_tatooine", "Scaley Hide", "Tatooinian", 12, 18, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_scaley_yavin4", "Scaley Hide", "Yavinian", 12, 18, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_wooly_corellia", "Wooly Hide", "Corellian", 12, 22, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_wooly_dantooine", "Wooly Hide", "Dantooine", 12, 22, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_wooly_dathomir", "Wooly Hide", "Dathomirian", 12, 22, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_wooly_endor", "Wooly Hide", "Endorian", 12, 22, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_wooly_lok", "Wooly Hide", "Lokian", 12, 22, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_wooly_naboo", "Wooly Hide", "Nabooian", 12, 22, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_wooly_rori", "Wooly Hide", "Rori", 12, 22, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_wooly_talus", "Wooly Hide", "Talusian", 12, 22, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_wooly_tatooine", "Wooly Hide", "Tatooinian", 12, 22, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_wooly_yavin4", "Wooly Hide", "Yavinian", 12, 22, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("meat_avian_corellia", "Avian Meat", "Corellian", 13, 10, min, max, 10000000, 10080000)
    

def generate10(core):
    res = core.resourceService
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("meat_avian_dantooine", "Avian Meat", "Dantooine", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("meat_avian_dathomir", "Avian Meat", "Dathomirian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("meat_avian_endor", "Avian Meat", "Endorian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("meat_avian_lok", "Avian Meat", "Lokian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("meat_avian_naboo", "Avian Meat", "Nabooian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("meat_avian_rori", "Avian Meat", "Rori", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("meat_avian_talus", "Avian Meat", "Talusian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("meat_avian_tatooine", "Avian Meat", "Tatooinian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("meat_avian_yavin4", "Avian Meat", "Yavinian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_carnivore_corellia", "Carnivore Meat", "Corellian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_carnivore_dantooine", "Carnivore Meat", "Dantooine", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_carnivore_dathomir", "Carnivore Meat", "Dathomirian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_carnivore_endor", "Carnivore Meat", "Endorian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_carnivore_lok", "Carnivore Meat", "Lokian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_carnivore_naboo", "Carnivore Meat", "Nabooian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_carnivore_rori", "Carnivore Meat", "Rori", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_carnivore_talus", "Carnivore Meat", "Talusian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_carnivore_tatooine", "Carnivore Meat", "Tatooinian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_carnivore_yavin4", "Carnivore Meat", "Yavinian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("meat_domesticated_corellia", "Domesticated Meat", "Corellian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("meat_domesticated_dantooine", "Domesticated Meat", "Dantooine", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("meat_domesticated_dathomir", "Domesticated Meat", "Dathomirian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("meat_domesticated_endor", "Domesticated Meat", "Endorian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("meat_domesticated_lok", "Domesticated Meat", "Lokian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("meat_domesticated_naboo", "Domesticated Meat", "Nabooian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("meat_domesticated_rori", "Domesticated Meat", "Rori", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("meat_domesticated_talus", "Domesticated Meat", "Talusian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("meat_domesticated_tatooine", "Domesticated Meat", "Tatooinian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("meat_domesticated_yavin4", "Domesticated Meat", "Yavinian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 700])
    res.createResource("meat_herbivore_corellia", "Herbivore Meat", "Corellian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 700])
    res.createResource("meat_herbivore_dantooine", "Herbivore Meat", "Dantooine", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 700])
    res.createResource("meat_herbivore_dathomir", "Herbivore Meat", "Dathomirian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 700])
    res.createResource("meat_herbivore_endor", "Herbivore Meat", "Endorian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 700])
    res.createResource("meat_herbivore_lok", "Herbivore Meat", "Lokian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 700])
    res.createResource("meat_herbivore_naboo", "Herbivore Meat", "Nabooian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 700])
    res.createResource("meat_herbivore_rori", "Herbivore Meat", "Rori", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 700])
    res.createResource("meat_herbivore_talus", "Herbivore Meat", "Talusian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 700])
    res.createResource("meat_herbivore_tatooine", "Herbivore Meat", "Tatooinian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 700])
    res.createResource("meat_herbivore_yavin4", "Herbivore Meat", "Yavinian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_insect_corellia", "Insect Meat", "Corellian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_insect_dantooine", "Insect Meat", "Dantooine", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_insect_dathomir", "Insect Meat", "Dathomirian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_insect_endor", "Insect Meat", "Endorian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_insect_lok", "Insect Meat", "Lokian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_insect_naboo", "Insect Meat", "Nabooian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_insect_rori", "Insect Meat", "Rori", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_insect_talus", "Insect Meat", "Talusian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_insect_tatooine", "Insect Meat", "Tatooinian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_insect_yavin4", "Insect Meat", "Yavinian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("meat_wild_corellia", "Wild Meat", "Corellian", 13, 10, min, max, 10000000, 10080000)
    

def generate11(core):
    res = core.resourceService
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("meat_wild_dantooine", "Wild Meat", "Dantooine", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("meat_wild_dathomir", "Wild Meat", "Dathomirian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("meat_wild_endor", "Wild Meat", "Endorian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("meat_wild_lok", "Wild Meat", "Lokian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("meat_wild_naboo", "Wild Meat", "Nabooian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("meat_wild_rori", "Wild Meat", "Rori", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("meat_wild_talus", "Wild Meat", "Talusian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("meat_wild_tatooine", "Wild Meat", "Tatooinian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("meat_wild_yavin4", "Wild Meat", "Yavinian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("milk_domesticated_corellia", "Domesticated Milk", "Corellian", 14, 7, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("milk_domesticated_dantooine", "Domesticated Milk", "Dantooine", 14, 7, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("milk_domesticated_dathomir", "Domesticated Milk", "Dathomirian", 14, 7, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("milk_domesticated_endor", "Domesticated Milk", "Endorian", 14, 7, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("milk_domesticated_lok", "Domesticated Milk", "Lokian", 14, 7, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("milk_domesticated_naboo", "Domesticated Milk", "Nabooian", 14, 7, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("milk_domesticated_rori", "Domesticated Milk", "Rori", 14, 7, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("milk_domesticated_talus", "Domesticated Milk", "Talusian", 14, 7, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("milk_domesticated_tatooine", "Domesticated Milk", "Tatooinian", 14, 7, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("milk_domesticated_yavin4", "Domesticated Milk", "Yavinian", 14, 7, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("milk_wild_corellia", "Wild Milk", "Corellian", 14, 7, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("milk_wild_dantooine", "Wild Milk", "Dantooine", 14, 7, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("milk_wild_dathomir", "Wild Milk", "Dathomirian", 14, 7, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("milk_wild_endor", "Wild Milk", "Endorian", 14, 7, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("milk_wild_lok", "Wild Milk", "Lokian", 14, 7, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("milk_wild_naboo", "Wild Milk", "Nabooian", 14, 7, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("milk_wild_rori", "Wild Milk", "Rori", 14, 7, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("milk_wild_talus", "Wild Milk", "Talusian", 14, 7, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("milk_wild_tatooine", "Wild Milk", "Tatooinian", 14, 7, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("milk_wild_yavin4", "Wild Milk", "Yavinian", 14, 7, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_crustacean_corellia", "Crustacean Meat", "Corellian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_crustacean_dantooine", "Crustacean Meat", "Dantooine", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_crustacean_dathomir", "Crustacean Meat", "Dathomirian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_crustacean_endor", "Crustacean Meat", "Endorian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_crustacean_lok", "Crustacean Meat", "Lokian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_crustacean_naboo", "Crustacean Meat", "Nabooian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_crustacean_rori", "Crustacean Meat", "Rori", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_crustacean_talus", "Crustacean Meat", "Talusian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_crustacean_tatooine", "Crustacean Meat", "Tatooinian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_crustacean_yavin4", "Crustacean Meat", "Yavinian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_fish_corellia", "Fish Meat", "Corellian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_fish_dantooine", "Fish Meat", "Dantooine", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_fish_dathomir", "Fish Meat", "Dathomirian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_fish_endor", "Fish Meat", "Endorian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_fish_lok", "Fish Meat", "Lokian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_fish_naboo", "Fish Meat", "Nabooian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_fish_rori", "Fish Meat", "Rori", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_fish_talus", "Fish Meat", "Talusian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_fish_tatooine", "Fish Meat", "Tatooinian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_fish_yavin4", "Fish Meat", "Yavinian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_mollusk_corellia", "Mollusk Meat", "Corellia", 13, 10, min, max, 10000000, 10080000)
    

def generate12(core):
    res = core.resourceService
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_mollusk_dantooine", "Mollusk Meat", "Dantooine", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_mollusk_dathomir", "Mollusk Meat", "Dathomirian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_mollusk_endor", "Mollusk Meat", "Endorian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_mollusk_lok", "Mollusk Meat", "Lokian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_mollusk_naboo", "Mollusk Meat", "Nabooian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_mollusk_rori", "Mollusk Meat", "Rori", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_mollusk_talus", "Mollusk Meat", "Talusian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_mollusk_tatooine", "Mollusk Meat", "Tatooinian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_mollusk_yavin4", "Mollusk Meat", "Yavinian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_reptilian_corellia", "Reptilian Meat", "Corellian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_reptilian_dantooine", "Reptilian Meat", "Dantooine", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_reptilian_dathomir", "Reptilian Meat", "Dathomirian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_reptilian_endor", "Reptilian Meat", "Endorian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_reptilian_lok", "Reptilian Meat", "Lokian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_reptilian_naboo", "Reptilian Meat", "Nabooian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_reptilian_rori", "Reptilian Meat", "Rori", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_reptilian_talus", "Reptilian Meat", "Talusian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_reptilian_tatooine", "Reptilian Meat", "Tatooinian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_reptilian_yavin4", "Reptilian Meat", "Yavinian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_egg_corellia", "Egg", "Corellian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("meat_egg_dantooine", "Egg", "Dantooine", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_egg_dathomir", "Egg", "Dathomirian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_egg_endor", "Egg", "Endorian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_egg_lok", "Egg", "Lokian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_egg_naboo", "Egg", "Nabooian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_egg_rori", "Egg", "Rori", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_egg_talus", "Egg", "Talusian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_egg_tatooine", "Egg", "Tatooinian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_egg_yavin4", "Egg", "Yavinian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("fiberplast_corellia", "Fiberplast", "Corellian", 1, 1, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("fiberplast_dantooine", "Fiberplast", "Dantooine", 1, 1, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("fiberplast_dathomir", "Fiberplast", "Dathomirian", 1, 1, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("fiberplast_endor", "Fiberplast", "Endorian", 1, 1, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("fiberplast_lok", "Fiberplast", "Lokian", 1, 1, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("fiberplast_naboo", "Fiberplast", "Nabooian", 1, 1, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("fiberplast_rori", "Fiberplast", "Rori", 1, 1, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("fiberplast_talus", "Fiberplast", "Talusian", 1, 1, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("fiberplast_tatooine", "Fiberplast", "Tatooinian", 1, 1, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("fiberplast_yavin4", "Fiberplast", "Yavinian", 1, 1, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("fiberplast_kashyyyk", "Fiberplast", "Kashyyykian", 1, 1, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 0, 600, 0])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("water_vapor_kashyyyk", "Water Vapor", "Kashyyykian", 4, 4, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_tidal_kashyyyk", "Tidal Renewable Energy", "Kashyyykian", 8, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("milk_domesticated_kashyyyk", "Domesticated Milk", "Kashyyykian", 14, 7, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("milk_wild_kashyyyk", "Wild Milk", "Kashyyykian", 14, 7, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("meat_domesticated_kashyyyk", "Domesticated Meat", "Kashyyykian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("meat_wild_kashyyyk", "Wild Meat", "Kashyyykian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 700])
    res.createResource("meat_herbivore_kashyyyk", "Herbivore Meat", "Kashyyykian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_carnivore_kashyyyk", "Carnivore Meat", "Kashyyykian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_reptilian_kashyyyk", "Reptilian Meat", "Kashyyykian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("meat_avian_kashyyyk", "Avian Meat", "Kashyyykian", 13, 10, min, max, 10000000, 10080000)
    

def generate13(core):
    res = core.resourceService
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_egg_kashyyyk", "Egg Meat", "Kashyyykian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_insect_kashyyyk", "Insect Meat", "Kashyyykian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_fish_kashyyyk", "Fish Meat", "Kashyyykian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_crustacean_kashyyyk", "Crustacean Meat", "Kashyyykian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_mollusk_kashyyyk", "Mollusk Meat", "Kashyyykian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 300, 0, 1, 400, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 500, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("bone_mammal_kashyyyk", "Animal Bones", "Kashyyykian", 10, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 100, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 700, 1, 600, 500, 500, 1, 1, 1000, 1])
    res.createResource("bone_avian_kashyyyk", "Avian Bones", "Kashyyykian", 10, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 200, 0, 1, 300, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 500, 700, 500, 1, 1, 1000, 1])
    res.createResource("bone_horn_kashyyyk", "Horn", "Kashyyykian", 11, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_bristley_kashyyyk", "Bristley Hide", "Kashyyykian", 12, 8, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_leathery_kashyyyk", "Leathery Hide", "Kashyyykian", 12, 15, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_wooly_kashyyyk", "Wooly Hide", "Kashyyykian", 12, 22, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_scaley_kashyyyk", "Scaley Hide", "Kashyyykian", 12, 18, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("corn_domesticated_kashyyyk", "Domesticated Corn", "Kashyyykian", 2, 9, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("corn_wild_kashyyyk", "Wild Corn", "Kashyyykian", 2, 9, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("rice_domesticated_kashyyyk", "Domesticated Rice", "Kashyyykian", 2, 17, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("rice_wild_kashyyyk", "Wild Rice", "Kashyyykian", 2, 17, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("oats_domesticated_kashyyyk", "Domesticated Oats", "Kashyyykian", 2, 16, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("oats_wild_kashyyyk", "Wild Oats", "Kashyyykian", 2, 16, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("wheat_domesticated_kashyyyk", "Domesticated Wheat", "Kashyyykian", 2, 21, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("wheat_wild_kashyyyk", "Wild Wheat", "Kashyyykian", 2, 21, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_greens_kashyyyk", "Vegetable Greens", "Kashyyykian", 2, 13, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_beans_kashyyyk", "Vegetable Beans", "Kashyyykian", 2, 6, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_tubers_kashyyyk", "Vegetable Tubers", "Kashyyykian", 2, 20, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_fungi_kashyyyk", "Vegetable Fungi", "Kashyyykian", 2, 12, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("fruit_fruits_kashyyyk", "Vegetable Fruits", "Kashyyykian", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("fruit_berries_kashyyyk", "Vegetable Berries", "Kashyyykian", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("fruit_flowers_kashyyyk", "Flower Fruit", "Kashyyykian", 2, 11, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 400, 300, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 800, 1, 1000, 400, 800, 1, 1, 1000, 1])
    res.createResource("wood_deciduous_kashyyyk", "Deciduous Wood", "Kashyyykian", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 600, 100, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 600, 1, 1000, 400, 300, 1, 1, 1000, 1])
    res.createResource("softwood_conifer_kashyyyk", "Conifer Wood", "Kashyyykian", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 800, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 500, 1, 1000, 400, 300, 1, 1, 1000, 1])
    res.createResource("softwood_evergreen_kashyyyk", "Evergreen Wood", "Kashyyykian", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 1, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 400, 500, 1])
    res.createResource("energy_renewable_unlimited_wind_kashyyyk", "Wind Renewable Energy", "Kashyyykian", 6, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 600, 500, 1])
    res.createResource("energy_renewable_unlimited_solar_kashyyyk", "Solar Renewable Energy", "Kashyyykian", 5, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_hydron3_kashyyyk", "Hydron-3 Renewable Energy", "Kashyyykian", 9, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 200, 500, 0])
    max = array('h', [1, 1, 1, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("energy_renewable_site_limited_geothermal_kashyyyk", "Geothermal Renewable Energy", "Kashyyykian", 7, 24, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("milk_domesticated_mustafar", "Domesticated Milk", "Mustafarian", 14, 7, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("milk_wild_mustafar", "Wild Milk", "Mustafarian", 14, 7, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("meat_domesticated_mustafar", "Domesticated Meat", "Mustafarian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("meat_wild_mustafar", "Wild Meat", "Mustafarian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 700])
    res.createResource("meat_herbivore_mustafar", "Herbivore Meat", "Mustafarian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_carnivore_mustafar", "Carnivore Meat", "Mustafarian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("meat_avian_mustafar", "Avian Meat", "Mustafarian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_egg_mustafar", "Egg Meat", "Mustafarian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("meat_insect_mustafar", "Insect Meat", "Mustafarian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_fish_mustafar", "Fish Meat", "Mustafarian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_crustacean_mustafar", "Crustacean Meat", "Mustafarian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("seafood_mollusk_mustafar", "Mollusk Meat", "Mustafarian", 13, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 300, 0, 1, 400, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 500, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("bone_mammal_mustafar", "Animal Bones", "Mustafarian", 10, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 100, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 700, 1, 600, 500, 500, 1, 1, 1000, 1])
    res.createResource("bone_avian_mustafar", "Avian Bones", "Mustafarian", 10, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 200, 0, 1, 300, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 500, 700, 500, 1, 1, 1000, 1])
    res.createResource("bone_horn_mustafar", "Horn", "Mustafarian", 11, 19, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_wooly_mustafar", "Wooly Hide", "Mustafarian", 12, 22, min, max, 10000000, 10080000)
    

def generate14(core):
    res = core.resourceService
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_bristley_mustafar", "Bristley Hide", "Mustafarian", 12, 8, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_leathery_mustafar", "Leathery Hide", "Mustafarian", 12, 15, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("hide_scaley_mustafar", "Scaley Hide", "Mustafarian", 12, 18, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("corn_domesticated_mustafar", "Domesticated Corn", "Mustafarian", 2, 9, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("corn_wild_mustafar", "Wild Corn", "Mustafarian", 2, 9, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("rice_domesticated_mustafar", "Domesticated Rice", "Mustafarian", 2, 17, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("rice_wild_mustafar", "Wild Rice", "Mustafarian", 2, 17, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("oats_domesticated_mustafar", "Domesticated Oats", "Mustafarian", 2, 16, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("oats_wild_mustafar", "Wild Oats", "Mustafarian", 2, 16, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 300, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 700])
    res.createResource("wheat_domesticated_mustafar", "Domesticated Wheat", "Mustafarian", 2, 21, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 300])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("wheat_wild_mustafar", "Wild Wheat", "Mustafarian", 2, 21, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_greens_mustafar", "Vegetable Greens", "Mustafarian", 2, 13, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_beans_mustafar", "Vegetable Beans", "Mustafarian", 2, 6, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_tubers_mustafar", "Vegetable Tubers", "Mustafarian", 2, 20, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("vegetable_fungi_mustafar", "Vegetable Fungi", "Mustafarian", 2, 12, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("fruit_fruits_mustafar", "Vegetable Fungi", "Mustafarian", 2, 12, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1000])
    res.createResource("fruit_berries_mustafar", "Vegetable Fungi", "Mustafarian", 2, 12, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 700, 1000, 1000])
    res.createResource("fruit_flowers_mustafar", "Flower Fruit", "Mustafarian", 2, 12, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 400, 300, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 800, 1, 1000, 400, 800, 1, 1, 1000, 1])
    res.createResource("wood_deciduous_mustafar", "Deciduous Wood", "Mustafarian", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 600, 100, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 600, 1, 1000, 400, 300, 1, 1, 1000, 1])
    res.createResource("softwood_conifer_mustafar", "Conifer Wood", "Mustafarian", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 800, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 500, 1, 1000, 400, 300, 1, 1, 1000, 1])
    res.createResource("softwood_evergreen_mustafar", "Evergreen Wood", "Mustafarian", 2, 10, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0])
    max = array('h', [1, 1, 600, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("petrochem_fuel_liquid_mustafar", "Liquid Petro Fuel", "Mustafarian", 1, 3, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1, 1, 1000, 1, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("fiberplast_mustafar", "Fiberplast", "Mustafarian", 1, 1, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 1, 0, 0, 0, 0, 0, 0, 600, 0])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("water_vapor_mustafar", "Water Vapor", "Mustafarian", 4, 4, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 750, 0, 0, 0, 0, 0, 870, 500, 0])
    max = array('h', [1, 1, 800, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("petrochem_fuel_solid_mustafar", "Solid Petro Fuel", "Mustafarian", 1, 1, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 900, 0, 0, 0, 0, 0, 907, 500, 0])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1000, 1000, 1])
    res.createResource("radioactive_mustafar", "Radioactive", "Mustafarian", 0, 25, min, max, 10000000, 10080000)
    
    min = array('h', [750, 600, 935, 948, 350, 935, 922, 0, 0, 1, 0])
    max = array('h', [800, 650, 1000, 1000, 400, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("steel_mustafar", "Steel", "Mustafarian", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [750, 450, 886, 919, 550, 903, 903, 0, 0, 1, 0])
    max = array('h', [800, 500, 1000, 1000, 600, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("iron_mustafar", "Iron", "Mustafarian", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [750, 750, 800, 750, 848, 800, 800, 0, 0, 500, 0])
    max = array('h', [800, 800, 800, 800, 1000, 900, 900, 1, 1, 1000, 1])
    res.createResource("aluminum_mustafar", "Aluminum", "Mustafarian", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [750, 500, 650, 600, 500, 750, 750, 0, 0, 500, 0])
    max = array('h', [800, 1000, 700, 650, 1000, 800, 800, 1, 1, 1000, 1])
    res.createResource("copper_mustafar", "Copper", "Mustafarian", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [896, 0, 909, 922, 550, 922, 922, 0, 0, 500, 0])
    max = array('h', [1000, 1, 1000, 1000, 600, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("ore_extrusive_mustafar", "Extrusive Ore", "Mustafarian", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [200, 0, 300, 700, 1, 500, 400, 0, 0, 1, 0])
    max = array('h', [1000, 1, 1000, 1000, 600, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("ore_intrusive_mustafar", "Intrusive Ore", "Mustafarian", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [870, 0, 870, 948, 550, 870, 870, 0, 750, 1, 0])
    max = array('h', [1000, 1, 1000, 1000, 600, 1000, 1000, 1, 800, 1000, 1])
    res.createResource("armophous_mustafar_1", "Type 1 Crystal Amorphous Gem", "Mustafarian", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [870, 0, 870, 948, 550, 870, 870, 0, 750, 1, 0])
    max = array('h', [1000, 1, 1000, 1000, 600, 1000, 1000, 1, 800, 1000, 1])
    res.createResource("armophous_mustafar_2", "Type 2 Crystal Amorphous Gem", "Mustafarian", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [838, 0, 838, 951, 350, 886, 886, 0, 919, 500, 0])
    max = array('h', [1000, 1, 1000, 1000, 400, 1000, 1000, 1, 1000, 1000, 1])
    res.createResource("crystalline_mustafar_1", "Type 1 Crystalline Gem", "Mustafarian", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [838, 0, 838, 951, 350, 886, 886, 0, 919, 500, 0])
    max = array('h', [1000, 1, 1000, 1000, 400, 1000, 1000, 1, 1000, 1000, 1])
    res.createResource("crystalline_mustafar_2", "Type 2 Crystalline Gem", "Mustafarian", 0, 0, min, max, 10000000, 10080000)
    
    min = array('h', [0, 0, 800, 0, 0, 0, 0, 0, 0, 500, 0])
    max = array('h', [1, 1, 1000, 1, 1, 1, 1, 1, 1, 1000, 1])
    res.createResource("gas_reactive_mustafar", "Reactive Gas", "Mustafarian", 3, 2, min, max, 10000000, 10080000)
    
    min = array('h', [1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1000, 1000, 1000, 1000, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("aluminum_perovskitic", "Aluminum", "Perovskitic", 15, 0, min, max, 10000000, 10080000)
    
    min = array('h', [1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1000, 1000, 1000, 1000, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("copper_borcarbantium", "Copper", "Borcarbantium", 15, 0, min, max, 10000000, 10080000)
    
    min = array('h', [1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1000, 1000, 1000, 1000, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("steel_bicorbantium", "Steel", "Bicorbantium", 15, 0, min, max, 10000000, 10080000)
    
    min = array('h', [1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1000, 1000, 1000, 1000, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("steel_arveshian", "Steel", "Hardened Arveshium", 15, 0, min, max, 10000000, 10080000)
    
    min = array('h', [1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1000, 1000, 1000, 1000, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("radioactive_polymetric", "Radioactive", "High Grade Polymetric", 15, 0, min, max, 10000000, 10080000)
    
    min = array('h', [1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1000, 1000, 1000, 1000, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("gas_reactive_organometallic", "Reactive Gas", "Unstable Organometallic", 15, 2, min, max, 10000000, 10080000)
    
    min = array('h', [1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0])
    max = array('h', [1000, 1000, 1000, 1000, 1000, 1000, 1000, 1, 1, 1000, 1])
    res.createResource("ore_siliclastic_fermionic", "Siliclastic Ore", "Fermionic", 15, 0, min, max, 10000000, 10080000)
    
