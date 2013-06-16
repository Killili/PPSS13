#!/bin/sh
java -cp PPSS13/tinyos.jar net.tinyos.sf.SerialForwarder -comm serial@/dev/ttyUSB0:telosb
