To run this application, make sure Docker is installed:

Then start a new Artemis server as docker image, like:
- docker run -it --rm -p 8161:8161 -p 61616:61616 vromero/activemq-artemis

The management console runs on localhost:8161

See: https://github.com/vromero/activemq-artemis-docker
