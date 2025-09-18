@echo off
echo Stopping cadett-splitters-sushi
docker stop cadett-splitters-sushi
echo Deleting container cadett-splitters-sushi
docker rm cadett-splitters-sushi
echo Deleting image cadett-splitters-sushi
docker rmi cadett-splitters-sushi
echo Running mvn package
call mvn package
echo Creating image cadett-splitters-sushi
docker build -t cadett-splitters-sushi .
echo Creating and running container cadett-splitters-sushi
docker run -d -p 9900:9900 --name cadett-splitters-sushi --network cadett-splitters-net cadett-splitters-sushi
echo Done!