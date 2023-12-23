# django-todo
A simple todo app built with django

![todo App](https://raw.githubusercontent.com/shreys7/django-todo/develop/staticfiles/todoApp.png)
### Setup
To get this repository, run the following command inside your git enabled terminal
```bash
$ git clone https://github.com/shreys7/django-todo.git
```
You will need django to be installed in you computer to run this app. Head over to https://www.djangoproject.com/download/ for the download guide

Once you have downloaded django, go to the cloned repo directory and run the following command

```bash
$ python manage.py makemigrations
```

This will create all the migrations file (database migrations) required to run this App.

Now, to apply this migrations run the following command
```bash
$ python manage.py migrate
```

One last step and then our todo App will be live. We need to create an admin user to run this App. On the terminal, type the following command and provide username, password and email for the admin user
```bash
$ python manage.py createsuperuser
```

That was pretty simple, right? Now let's make the App live. We just need to start the server now and then we can start using our simple todo App. Start the server by following command

```bash
$ python manage.py runserver
```

Once the server is hosted, head over to http://127.0.0.1:8000/todos for the App.

Cheers and Happy Coding :)

Streamlining Django app Deployment with CI/CD using Jenkins and AWS
Introduction: Hello folks this is my first blog :). I have forked Django to do app repository from here. Continuous Integration and Continuous Deployment (CI/CD) have become essential practices in the world of modern software development. They automate the testing and deployment of applications, ensuring that code is built, tested, and shipped to production on a consistent basis. In this blog post, we will look at how to use Jenkins for CI/CD to deploy a Django application to an AWS server. The associated GitHub repository can be found here for reference. Below is a simple overview of my deployment flow.
 Deployment process
Setting Up the Environment: Before we dive into the details, you’ll need a few prerequisites. First, you should have a Django application that you wish to deploy. Secondly, you’ll need an AWS account to host your application. Additionally, Docker plays a crucial role in this deployment process. The heart of the Dockerization process is the Dockerfile, which outlines the necessary dependencies and configurations.
Creating a Jenkins Pipeline: Jenkins is a powerful tool for streamlining the CI/CD process. It enables you to automate building, testing, and deploying your Django application. The key to this automation is the Jenkinsfile. This file defines the stages and steps of the pipeline, orchestrating the entire process seamlessly. I have mentioned dummy jenkinsfile on my GitHub repository I mentioned above in introduction. 
1.	Create Jenkins pipeline (click on + New Item)
  Select Pipeline

 
2.
Deploying to AWS: AWS (Amazon Web Services) is a popular cloud platform for hosting applications. To deploy your Django application, you’ll first need to set up an AWS server. This typically involves choosing the appropriate instance type and configuring your AWS environment. You’ll also need to establish the necessary security measures, such as defining network rules and access controls. For this project you can use t2.micro as it is in free tier. Also you need to open port 8123 & 5522(SSH) in security group.
 Inbound Rule
Running the Django Application: The heart of your Django application deployment is the startup.sh script, which serves as the entry point for the Docker container. When you start the Docker container, the script ensures that your Django application is up and running on port 8123, ready to serve incoming requests.
Testing the CI/CD Workflow: An integral part of the CI/CD process is testing. Every change in your GitHub repository triggers the Jenkins pipeline, automating the building, testing, and deployment of your application. This ensures that any code changes are thoroughly examined before they reach production, resulting in a more robust and reliable application. You can integrate Jenkins and git 



