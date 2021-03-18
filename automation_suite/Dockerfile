FROM  539909726087.dkr.ecr.us-west-2.amazonaws.com/base-hardened-jre:stable

LABEL org.opencontainers.image.title="niagara-cxp-api-automation" \
      org.opencontainers.image.description="CHANGEME" \
      org.opencontainers.image.url="git@gitlab-sjc.cisco.com:cx-india-qa/api-automation-cxp.git" \
      org.opencontainers.image.source="git@gitlab-sjc.cisco.com:cx-india-qa/api-automation-cxp.git" \
      org.opencontainers.image.vendor="Cisco CXE" \
      org.opencontainers.image.revision="$VCS_REF" \
      org.opencontainers.image.created="$BUILD_DATE" \
      org.label-schema.schema-version="1.0" \
      org.label-schema.name="api-automation-cxp" \
      org.label-schema.description="CHANGEME" \
      org.label-schema.url="git@gitlab-sjc.cisco.com:cx-india-qa/api-automation-cxp.git" \
      org.label-schema.vcs-url="git@gitlab-sjc.cisco.com:cx-india-qa/api-automation-cxp.git" \
      org.label-schema.vendor="Cisco CXE" \
      org.label-schema.vcs-ref="$VCS_REF" \
      org.label-schema.build-date="$BUILD_DATE"


RUN mkdir -p /app
WORKDIR /app

COPY target/niagara-automation-suite-for-api-1.0.0-SNAPSHOT.jar ./niagara-automation-suite-for-api.jar

#AppD environment variables
ENV APPD_CONF=""
ENV APPDYNAMICS_ENABLED=""

COPY docker-entrypoint.sh /bin/docker-entrypoint.sh
RUN chmod +x /bin/docker-entrypoint.sh
ENTRYPOINT ["/bin/docker-entrypoint.sh", "/app/niagara-automation-suite-for-api.jar"]
