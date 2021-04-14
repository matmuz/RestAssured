# RestAssured

This is a sample project that uses REST-assured to test restful-booker API. There is a small set of tests that covers basic cases of all the methods provided by the API.

To run the tests (Java and Maven required!) simply download the project and run the tests from an IDE

If you want to run tests from CMD:

    1. Navigate to the project using CMD
    2. Run mvn clean test command

*Optionally, if you want to see a report there is a surefire-report plugin so feel free to use it:

    1. mvn surefire-report:report
    2. if you are lacking CSS in the report use: mvn site -DgenerateReports=false