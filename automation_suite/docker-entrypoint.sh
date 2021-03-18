#!/bin/sh

## Appd configuration
if [ ! -z "$APPDYNAMICS_ENABLED" ]; then
    APPD_CONF="-javaagent:/AppServerAgent/javaagent.jar -Dappdynamics.agent.uniqueHostId=$(sed -rn '1s#./##; 1s/(.{12})./\1/p' /proc/self/cgroup) ${APPDYNAMICS_JVM_PARAMETER}"
fi

## Spring boot app.
java -jar $1

## EOF
