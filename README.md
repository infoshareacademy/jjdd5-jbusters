# JBusters

##### I. Instructions to run the Web App on Docker:

1. Open CLI, navigate to the project main directory and enter the following:

    ``` shell
    $ sh start_docker.sh
    ```

2. Thereafter open your browser and go to the following address: 0.0.0.0:4280

- *Optional - to read the Web App logs enter the following in CLI:*

    ``` shell
    $ sudo docker exec -it jjdd5-jbusters_app_1 bash
    $ cd /opt/jboss/wildfly/standalone/log/
    $ tail -f server.log
    ```

##### II. Online version can be found under the following link: [JBusters Web App](http://jbusters.jjdd5.is-academy.pl "JBusters Web App")