#!/bin/sh -l
set -e 

cd /home/core

cat << EOF > app-${commitId}.yaml
---
apiVersion: v1
kind: ReplicationController
metadata:
  name: gs-rest-service-${shortCommitId}
  namespace: default
  labels:
    app: gs-rest-service
    version: "${commitId}"
spec:
  replicas: 2 
  template:
    metadata:
      labels:
        app: gs-rest-service
        version: "${commitId}"
    spec:
      containers:
        - name: gs-rest-service
          image: registry.platform.svc.appfactory.local:5000/gs-rest-service:${commitId}
          env:
            - name: DATABASE_URL
              value: 'postgres://devdata@postgres.default.svc.appfactory.local/devdata'
            - name: JAVA_OPTS
              value: '-Xmx500m -Xms500m'
          resources:
            limits:
              cpu: 900m
              memory: 450Mi
            requests:
              cpu: 900m
              memory: 450Mi
          ports:
            - containerPort: 8080
              name: "http-server"
          livenessProbe:
            httpGet:
             path: /
             port: 8080
            initialDelaySeconds: 600
            timeoutSeconds: 5
            successThreshold: 1
            failureThreshold: 5
EOF
/opt/bin/kubectl create -f app-${commitId}.yaml

cat <<EOF > service-${commitId}.yaml
---
kind: Service
apiVersion: v1
metadata:
  name: gs-rest-service-${shortCommitId}
spec:
  type: NodePort
  ports:
    - port: 80
      protocol: TCP
      targetPort: 8080
  selector:
    app: gs-rest-service
    version: "${commitId}"
EOF

/opt/bin/kubectl create -f service-${commitId}.yaml