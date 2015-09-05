TaskViewer
=====

TaskViewer is for business operators to perform tasks and execute business processes.

## Development

This is a Maven project powered by Spring MVC framework. The following commands are useful for local development and testing:


**Gradle** (recommended)
Make sure Gradle is installed in your machine. Try `gradle -v` command. Otherwise install in from [http://www.gradle.org/](http://www.gradle.org/).

```
# Install artifacts to your local repository
gradle install

# Start the web application
gradle jettyRun
```

Now, open your browser and view `http://localhost:8080/TaskViewer`.

**Maven**

```
mvn install -DskipTests
mvn clean jetty:run
```


