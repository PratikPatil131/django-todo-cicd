#!/bin/bash

# Create a detached screen session and run a command within it
screen -dmS django bash -c 'cd django-todo-cicd && python manage.py runserver 0.0.0.0:8123'

