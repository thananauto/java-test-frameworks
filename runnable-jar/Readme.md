# runnable-jar
This repo is to demonstrate, how to execute the test autoamtion framework
as runnable jar in containerized environment

Here I tried to combine standard testng project and cucumber testng project together,
you can find the runnable class at `src\test\com\qa\test\TestTestNG.java` file

The maven assembly plugin are used to create a runnable jar, refer the customised
assembly descriptor for packaging the test folder, watch out the file in `src/test/resources/test.xml`