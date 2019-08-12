Based on the criteria of the assignment `parking-assignemt` contains the project so implemented in java. It used gradle for build management. Test cases for two modules have also been added.

# Setup
After moving to `parking-assignment` dir:
One can build the application by running
	`./gradlew test jar`
This downloads the needed set of dependencies and creates a fat jar in dist dir.
The name of the generated jar is `parking-assignment-1.0-SNAPSHOT.jar`

This can be done using `bin/setup` script also.

# Usage
The jar can be run in 2 modes:
1. Interactive Mode
2. File Mode

1. Interactive Mode
Jar can be run in interactive mode by:
	`java -jar parking-assignment-1.0-SNAPSHOT.jar`
			or
		`bin/parking_lot`
This gives a prompt starting with `$ `. One can enter commands on the prompt after this.

2. File Mode
Jar can be run in file mode by:
	`java -jar parking-assignment-1.0-SNAPSHOT.jar <filename> `
			or
		`bin/parking_lot <filename>`
The commands that are there in file will be run one by one.

# Implementation Details
The ParkingLot supports following commands(<syntax>, <arg count>):
```
CREATE_PL("create_parking_lot", 1),
PARK("park", 2),
UNPARK("leave", 1),
STATUS("status", 0),
GET_REGNO_FOR_CAR_COLOR("registration_numbers_for_cars_with_colour", 1),
GET_SLOTNO_FOR_CAR_COLOR("slot_numbers_for_cars_with_colour", 1),
GET_SLOTNO_FOR_REGNO("slot_number_for_registration_number", 1),
EXIT("exit", 0);
```

Various error cases have been taken into account when building this such as invalid value of arguments in case of Parking lot creation, Invalid slot numbers when unparking the cars and trying to par a car with previously parked reg no.

With respect to implementation, CommandProcessing has been segregated from DataStore.

Class `AbstractCommandProcessor` takes care of the command processing.
Class `ParkingLot` takes care of the storing of parking lot state.

Some enhancements in implementation can be:
Use of BufferedWritter to print output to System.out instead of creating one big string when processing commands