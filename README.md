# Technology Radar

This got started from Neal Fords blog post a few years back about the technology radar process at Thoughtworks.  I started this project with the idea of taking that javascript example and putting a database behind it.  

Since then Thoughtworks came up with a version that backs the javascript with a Google sheet.  However one thing that I think is still lacking is historical searching across
a number of different instances of the tech radar.  So I started this back up again for my own personal use.

## Getting Started

Once you've cloned the source control.  Start by going to the Database folder and running the scripts in order.  This should create the database with the default data that is neccessary to run the app.

From there open the project in your favorite ide, import the maven dependencies and you should be able to just run it.

The app authenticates with Auth0, so you will need to setup an account and login with them.   

Once you do that and you are logged into the application, go to the Admin Section, create your first Assessment (I may be renaming this to Radar Session, TBD).

Finally go back to the main page (/home/secureradar) and enter your radar items.

### Prerequisites
The scripts are written for MySql and I use Intellij as my IDE.  

## Running the tests

No tests yet.  I'll be honest I'm more accustomed to .net so I have yet to look into the unit test process of java.


## Deployment

Deployment is coming in the next branch.  It will be deployed in a container to AWS.

## Built With

* [Java SDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) - Java SDK
* [Spring Boot](https://projects.spring.io/spring-boot/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Intellij](https://www.jetbrains.com/idea/) - IDE
* [MySQL](https://www.mysql.com/) - Database
* [Auth0](https://auth0.com/) - Identity

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

Not much versioning yet beyond what is in master.

## Authors

* **Arthur Correa** - *Initial work* - [AlwaysMoveForward.com](http://www.alwaysmoveforward.com)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* The inspiration for this was the Thoughtworks Technology Radar process.  
