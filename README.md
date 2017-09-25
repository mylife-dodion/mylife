### MyLife Digital – Programming Task 2017

This project is organised as a standard Maven project 
comprising a parent project **mylife** with two modules 
i.e. **stats** and **demo**. The code targets Java 8. 
Therefore, _Maven_ (minimum version 3) and _Java 8 JDK_ are required to be 
installed.

From the project's root directory i.e. _**mylife**_ the Maven commands 

```mvn clean install``` 

will build all modules. Alternatively, running the same commands 
from either of the module root directories will build that 
module only.

#### stats module

This contains the code for the library that will process a selected text 
file containing integers in csv format and generate a new immutable object 
containing the following information about the file's contents:
* Total number of integers,
* Mean value of all integers (to three decimal places),
* Highest number of integers in a single line,
* Most common integer. (_Of course, more than one integer may share the 
status of being most common so this is returned as a collection._)

The library can be introduced as a dependency to any Maven based project by updating the pom.xml file as has been done in the Demo program e.g. 
```
 <dependencies>
    ...
    <dependency>
      <groupId>com.example</groupId>
      <artifactId>stats</artifactId>
      <version>1.0.0.0</version>
    </dependency>
    ...
  </dependencies>
  ```

#### demo module

This contains a minimal executable java program (fat jar) to demonstrate the use 
of the stats library. After building the project and navigating to the 
demo target directory i.e. demo/target the program can be run with the 
command 

```java -jar demo-1.0.0.0.jar ../../stats/target/test-classes/normal.csv```

The last part of the command is the path to the selected csv file so can be changed 
to select any appropriate file. The demo program will display the statistical 
information about the file in the console e.g.

```
Demo program is processing csv file /Users/fjp/mylife/stats/target/test-classes/normal.csv ...
Total number of integers = 26
Mean value of all integers (to three decimal places) = 81.962
Highest number of integers in a single line = 12
Most common integer = [8, 123]
```
Alternatively, if there is a problem with the file you should see something similar to:
```
Demo program is processing csv file /Users/fjp/mylife/stats/target/test-classes/corrupted.csv ...
There was a problem processing the selected file [ /Users/fjp/mylife/stats/target/test-classes/corrupted.csv ]. Full details are available in the log file.
```

Log files are configured to be placed in **logs/demo-application.log** relative to the running program.
