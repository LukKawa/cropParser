# Cron Parser
Application is build with gradle. 
To build app you need to use command:

```  
./gradlew clean build
```

To run application you need to use command

```
java -jar build/libs/cronParser-0.0.1-SNAPSHOT.jar "*/15 0 1,15 * 1-5 /usr/bin/find"

```

I believe that jvm application is unfortunate choice for CLI application so i prepared it for reusage in some service.  
I've used picocli as lightweight framework to processi spring boot cli. It's maybe small slowdown but it could be simply extended to expose endpoint and run as a service. 

I've been thinking about introducing CropOperatorService interface and creating implementation of service for each [CronOperatorType](src%2Fmain%2Fjava%2Fcom%2Fshift%2Fparser%2Fmodel%2FCronOperatorType.java) it would help in making easy introducing new aliases and functionalities, but it seems a bit overengineered as of now.
