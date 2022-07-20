## README
### (Written as part of the course project)
This is the third iteration of Daniel Harnden's Course Degree Program Browser. The program is written in Java and HTML, and uses Maven, Jsoup, and GSON. The purpose of this project is to scrape information from Tennessee Technological University's public course catalog and present it to the user. This program stores the gathered data as JSON files for quicker load times on subsequent searches.

#### How to Clone Repository
To clone the repository, simply enter 
> git clone https://gitlab.csc.tntech.edu/csc2310-fa21-students/dpharnden42/dpharnden42-coursepr-web-dev.git
>
into the command line and follow the on screen prompts. This should result in a folder titled dpharnden\-coursepr\-web\-dev.

#### How to Compile and Run Program
Typing
> mvnw spring-boot:run
>
in the dpharnden\-coursepr\-web\-dev directory will compile and run the program using Maven Project Management Tool. From there, a local web application will be running at 
> http://localhost:8080/
>
where you can test the program.

#### Structure of Directories:
The dpharnden\-coursepr\-web\-dev directory contains:<br>
* .idea - Contains project information used by IntelliJ.
* .mvn - Contains the Maven Wrapper.
* docs - Contains Use Case Diagrams and other pre-coding documentation.
* src - Contains the Java code and test classes.
* target - The default Maven output folder.
* .gitignore - The gitignore file used by GitLab.
* mvnw (file) - USED BY UNIX BASED SYSTEMS - Allows you to run the Maven project without having Maven installed on the path.
* mvnw (.cmd) - USED BY WINDOWS BASED SYSTEMS - Allows you to run the Maven project without having Maven installed on the path.
* pom.xml - The Project Object Model used by Maven when building the project. 
* README - The README file you are currently reading.
* sample - Example HTML code

#### List of Dependencies:

* [Maven](https://maven.apache.org/)
* [JSoup](https://jsoup.org/)
* [JUnit](https://junit.org/junit5/)
* [GSON](https://github.com/google/gson)