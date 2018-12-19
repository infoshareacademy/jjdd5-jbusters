FROM jboss/wildfly

ADD target/web-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/

RUN /opt/jboss/wildfly/bin/add-user.sh admin admin --silent

RUN mkdir -p /opt/jboss/wildfly/data
RUN mkdir -p /opt/jboss/wildfly/upload

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
