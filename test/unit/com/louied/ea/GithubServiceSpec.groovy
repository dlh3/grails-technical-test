package com.louied.ea;

import grails.converters.JSON

@TestFor(GithubService)
class GithubServiceSpec extends spock.lang.Specification {

	def 'the github API host is correct'() {
		expect: 'the host has not changed'
			service.apiHost == 'https://api.github.com/'
	}

	def 'can retrieve a github API URL for given path components'() {
		given: 'the path components of the URL'
			String[] pathParts = ['controller', 'username', 'some-action']

		when: 'the service is called to retrieve a URL with the given path components'
			def url = service.getGithubUrlForPath(pathParts)

		then: 'the URL has been constructed with the correct location'
			url.toString() == "${service.apiHost}${pathParts.join('/')}"
	}

	def 'can retrieve a github API URL with pre-assembled path'() {
		given: 'the path of the URL is pre-assembled'
			String path = 'controller/username/some-other-action'

		when: 'the service is called to retrieve a URL with the given path'
			def url = service.getGithubUrlForPath(path)

		then: 'the URL has been constructed with the correct location'
			url.toString() == "${service.apiHost}${path}"
	}

	def 'can find repositories for the given username'() {
		given: 'a username and corresponding repositories'
			def username = 'username'
			def expectedRepos = """
[
  {
    "id": 19249503,
    "full_name": "dlh3/grails-petclinic",
    "owner": {
      "login": "dlh3",
      "id": 648035,
      "url": "https://api.github.com/users/dlh3",
    },
    "private": false,
    "url": "https://api.github.com/repos/dlh3/grails-petclinic",
    "watchers": 0,
    "default_branch": "master"
  }
]
"""

		when: 'the github user is found and a URL (with HTTP text response) is returned'
			service.metaClass.getGithubUrlForPath = { Map queryParams = [:], String...pathParts ->
				assert ['users', username, 'repos'] == pathParts
				[text: expectedRepos]
			}

		and: 'the service is called to retrieve JSON'
			def repos = service.findRepositoriesByUsername(username)

		then: 'the result is an object corresponding to the JSON formatted HTTP response'
			repos == JSON.parse(expectedRepos)

		cleanup: 'revert the GithubService metaClass change'
			service.metaClass = service.class.metaClass
	}

	def 'find repositories returns null when github user is not found'() {
		given: 'a non-existent username'
			def username = 'non-existent_user'

		when: 'the github user is not found'
			service.metaClass.getGithubUrlForPath = { String...pathParts ->
				def url = new Expando();
				url.text = { -> throw new FileNotFoundException()}()
				return url
			}

		and: 'the service is called to retrieve JSON'
			def repos = service.findRepositoriesByUsername(username)

		then: 'the result is null'
			repos == null

		cleanup: 'revert the GithubService metaClass change'
			service.metaClass = service.class.metaClass
	}

	def 'find repositories returns null when given username is null'() {
		given: 'a null username'
			def username = null

		when: 'the service is called to retrieve JSON'
			def repos = service.findRepositoriesByUsername(username)

		then: 'the result is null'
			repos == null
	}

	def 'find repositories returns null when given username is empty'() {
		given: 'an empty username'
			def username = ''

		when: 'the service is called to retrieve JSON'
			def repos = service.findRepositoriesByUsername(username)

		then: 'the result is null'
			repos == null
	}
}
