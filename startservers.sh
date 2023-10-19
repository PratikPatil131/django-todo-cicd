#!/bin/bash
#sh /apache-tomcat-9.0.74/bin/startup.sh
#service apache2 start && tail -f /dev/null
screen -dmS django && bash -c 'cd django-todo-cicd && python manage.py makemigrations && python manage.py migrate && python manage.py runserver 0.0.0.0:8080'
