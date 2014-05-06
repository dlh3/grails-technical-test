# EA Grails Technical Test - Completed by Dave Hughes (dlh3)

## Users
In environment=development
* admin:admin
* user:user

In environment=production
* admin:free2play
* dlh3:dlh3


## Log
Tuesday, May 6, 2014 - 1:07PM PST
* Challenge started

1:12PM
* Missing applicationContext.xml

1:16PM
* app.name property is incorrect

1:25PM
* Added Spring Security

1:30PM
* Added BootStrap for user creation (see users section at top)

1:35PM
* Added static rule for ROLE_ADMIN access to H2 web console
* Added Spock plugin for unit tests

1:45PM
* Added Github Controller and associated view (and messages)

2:15PM
* Added Github Service to lookup repositories by username from github API



#################
# EA Grails Technical Test

### Task

* The task is implement a website that allows the user to login to search the Github repoistories.
* You have 4 hours to complete the test.
* Please finish all the items outlined in the Requirements section first, then try to tackle items in the Nice to Haves section if you have time.
* If you cannot finish the test, please explain why as we are reasonable and realize people have time constraints.

### Setup

* You can find the design under the design folder
* You would need Grails 2.1.4 to run the application

### Requirements

* We expect you to leverage the powers of Grails plugins to install an authentication plugin. Eg spring-security-plugin
* The login form should have server side validation
* All pages should be gated by the login page if the user is not login.
* You are free to use any http client to call out to Github's API
* Have a search field that allows searching for a GitHub user's repositories. See http://developer.github.com/v3/repos/#list-user-repositories for more info. Call the following API (where USER_NAME is the value typed into the search field):
```
https://api.github.com/users/USER_NAME/repos
```
* Once the search is clicked, the results should show a list of that user's public repositories with each item in a "name/number of watchers" format.
* The results should be in json format for the view to consume
* Use h2 as the datasource
* It's not a requirement to style the pages
* We expect to have unit test code coverage of your code

### Nice to Haves

* When a result is clicked, display an alert box with the repository's ID and the created_at time.
* Use AngularJS to display the repositories results
* Extended functionalities where you see fit.

### Deliverables

* Please fork this project on GitHub and add your code to the forked project.
* Update the README file to include the time you spent and anything else you wish to convey.
* Send the link to your forked GitHub project to your recruiter.

*Good luck!**
