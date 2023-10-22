FROM ubuntu:22.04
FROM python:3.7.9
# Install required packages for both applications
RUN apt-get update && apt-get upgrade -y && apt install -y \
    openjdk-11-jdk \
    build-essential \
    curl \
    wget \
    unixodbc \
    git \
    screen \
    vim \
    && rm -rf /var/lib/apt/lists/*



RUN apt update
RUN apt-get install python3-pip -y

RUN git clone https://github.com/PratikPatil131/django-todo-cicd.git

WORKDIR /django-todo-cicd


EXPOSE 8123


RUN python3.7 -m pip install -r requirements.txt

WORKDIR /
COPY startservers.sh /
RUN chmod +x /startservers.sh

ENTRYPOINT ["/bin/sh", "-c", "/startservers.sh && /bin/sh"]
