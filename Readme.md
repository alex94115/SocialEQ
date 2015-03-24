# SocialEQ

# System Components

There are three runtime components: a batch job, a web application, and a Twitter stream listener. These three components, plus a library of shared Java code (rodlaver-foundation), comprise the system.

1. Batch job - runs frequently and polls the database for "work" that needs to be done. "Work" is represented by rows in the database that meet certain criteria, e.g., a row in the Messages table with status "PROCESSING". The batch job does the work and modifies row statuses to keep track of items it has already processed. So that the batch job can do a lot of work quickly, all data processing in the batch job is done using explicit SQL statements (i.e., not via JPA / Hibernate) along with SQL mass update statements. The batch job is built in Java based on the Spring Batch framework. 
2. Web application - runs in a Java Servlet Container and processes web requests. The web application is built using the Spring framework and Spring MVC. Dynamic HTML is produced by the Thymeleaf engine. ORM is provided by JPA / Hibernate.
3. Twitter Stream Listener - leverages Twitter4j and the Hosebird client to provide a high-performance Twitter stream listener. This component's main responsibility is to establish a connection to the Twitter REST Stream and to write tweets that are sent over the stream into the database for further processing.

# Build Prerequisites

1. Apache Maven is used to build this project. I used version 3.2.5 on MacOS.
2. To compile the source requires Java. I used version 1.7.0_60.
3. To deploy requires a server environment with a Java application container. I used an Amazon Web Services ElasticBeanstalk Tomcat environment.
4. A MySQL database is required for transactional data storage. I used version 5.6.20.


# Building

1. Clone the repository.
2. Within the rodlaver-parent directory, add the parent.pom to your local maven repository so it will be available to the subsequent build steps:

        ~/rodlaver-parent $ mvn --non-recursive clean install

3. Within the rodlaver-foundation directory, build the shared jar:

        ~/rodlaver-foundation $ mvn clean package install

4. Within the rodlaver-twitter-stream directory, build the Twitter stream listener:

        ~/rodlaver-twitter-stream $ mvn clean package install

5. Within the rodlaver-jdbc-batch directory, build the batch job. There are a variety of environment properties required for this build to work; you can either externalize these values or pass them in using the -DargLine="" argument to the mvn build.

        ~/rodlaver-jdbc-batch $ mvn -DargLine="-DfirstKey=firstValue -secondKey=secondValue" clean install 

6. Within the rodlaver directory, build the web application.

        ~/rodlaver $ mvn clean compile test war:war

# Deploying

1. Create the MySQL database named hootit (TODO: provide DDL script).
2. Copy the twitter stream and batch job distribution.tar.gz files to the destination environment, each in its own subdirectory. Un-tar the distribution files and make the scripts in the bin/ subdirectories executable. Run initiateStream.sh and initiateBatch.sh to start the twitter stream and batch job.
3. Deploy the rodlaver WAR file to a Tomcat container to make the web application available.
