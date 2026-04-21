aplicação para espelhar uma request no log, separando parametros, header e body

Local:

docker build -t mirror-app:latest .


OpenShift:

oc new-build --binary --name=mirror-app
oc start-build mirror-app --from-dir=. --follow
oc new-app mirror-app
oc expose svc/mirror-app