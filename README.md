# JBusters

##### I. Instructions to run the Console App:

- Open CLI, navigate to the 'app' subdirectory in the project and enter the following:

    ``` shell
    $ mvn clean package
    $ sh run_app.sh
    ```

##### II. Instructions to run the Web App:

1. Open CLI, navigate to the project main directory and enter the following:

    ``` shell
    $ mvn clean package
    $ sudo docker-compose up -d --build --force-recreate
    ```

2. Thereafter open your browser and go to the following address: 0.0.0.0:4280

- *Optional - to read the Web App logs enter the following in CLI:*

    ``` shell
    $ sudo docker exec -it jjdd5-jbusters_app_1 bash
    $ cd /opt/jboss/wildfly/standalone/log/
    $ tail -f server.log
    ```

##### III. Online version can be found under the following link: [JBusters Web App](http://jbusters.jjdd5.is-academy.pl "JBusters Web App")