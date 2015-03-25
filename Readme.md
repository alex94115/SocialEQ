# SocialEQ

# Functional Overview

SocialEQ is a "social transactions" system built on top of Facebook and Twitter. The core business process is that a service provider offers a good or service via a social site. Each offer is accompanied by a specific #hashtag. Users who are interested in the offer can claim it by repeating the #hashtag back to the service provider, which causes SocialEQ to facilitate the transaction. By way of an analogy, the #hashtag is like a Buy Button for a specific item and users can "click" the Buy button by using the #hashtag on a post to Facebook or Twitter.

## What Types of Goods and Services can be Offered via SocialEQ? 

SocialEQ supports three types of goods: 1) digital content like audio and video files, 2) physical items (that must be delivered to the recipient), and 3) stored value vouchers. Each of these goods can be offered for sale or as part of a giveaway.

## How Do Buyers Initiate a Purchase / How Can a Person Enter a Giveaway?

The service provider chooses whether their offer is made available on one or both of Facebook and Twitter. SocialEQ will automatically publish the offer. 

### Facebook 
On Facebook, the embodiment of an offer is a Photo within an Album having a caption that describes the offer and specifies the #hashtag. Users can initiate a transaction by repeating the #hashtag within either: 1) a comment on the Photo itself, or 2) a Post to the service provider's Facebook Page. 

### Twitter
On Twitter, the offer is a Tweet using the Product Card format that includes a preview image and summary information about the offer. Users can initiate a transaction by repeating the #hashtag within either: 1) a reply to the offer Tweet, or 2)  a Tweet that @mentions the service provider.

## Payments

SocialEQ allows service providers to set the price for a given good or service. After a buyer initiates a purchase by using the #hashtag, SocialEQ automatically replies with a link that brings the user into the transaction flow. SocialEQ authenticates users via OAuth and registers new users. Next, a checkout flow starts that allows a user to make a purchase using a credit or debit card. SocialEQ integrates with a payment service to effect the transactions and stores a payment "token" that can be used for subsequent payments from this user.

In this way, the "next" transaction from a customer can be done frictionlessly by clearing the payment in the background without a checkout flow.

## Fulfillment

SocialEQ hosts digital download files, and users who have either bought or won access are presented with a secure download link on a transaction detail page. Stored voucher-style products work in a similar way; a PDF is generated and stored securely for each buyer / winner.

For physical products, SocialEQ facilitates the exchange of shipping information and provides a workflow for tracking the status of items during the delivery process.

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

1. For the database, create the MySQL database named hootit using the [DDL script](rodlaver/src/main/resources/sql/hootit-prod.sql), then create three users 'batch', 'stream', and 'hootit'. Next run the [grants script](rodlaver/src/main/resources/sql/hootit-user-grants.sql) to assign the appropriate privileges to these users.
2. Copy the twitter stream and batch job distribution.tar.gz files to the destination environment, each in its own subdirectory. Un-tar the distribution files and make the scripts in the bin/ subdirectories executable. Run initiateStream.sh and initiateBatch.sh to start the twitter stream and batch job.
3. Deploy the rodlaver WAR file to a Tomcat container to make the web application available.
