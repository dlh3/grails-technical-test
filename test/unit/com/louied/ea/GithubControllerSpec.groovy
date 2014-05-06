package com.louied.ea;

@TestFor(GithubController)
class GithubControllerSpec extends spock.lang.Specification {

	def setup() {
		controller.githubService = Mock(GithubService)
	}

	def 'the default action is "find"'() {
		expect: 'the default action is unchanged'
			controller.defaultAction == 'find'
	}

	def 'the GithubService is not queried on GET without ID'() {
		when: 'the request is GET'
			controller.request.method = 'GET'

		and: 'ID is null'
			controller.params.id = null

		and: 'the controller is invoked'
			def model = controller.find()

		then: 'there is no model returned'
			model == null

		and: 'the GithubService is not queried'
			0 * controller.githubService.findRepositoriesByUsername(_)
	}

	def 'the GithubService is not queried on GET with ID!="current"'() {
		when: 'the request is GET'
			controller.request.method = 'GET'

		and: 'ID is present but not "current"'
			controller.params.id = 'somethingElse'

		and: 'the controller is invoked'
			def model = controller.find()

		then: 'there is no model returned'
			model == null

		and: 'the GithubService is not queried'
			0 * controller.githubService.findRepositoriesByUsername(_)
	}

	def 'the GithubService is queried on GET with ID=="current"'() {
		given: 'the user is logged in'
			def loggedInUsername = 'someLoggedInUser'
			controller.springSecurityService = [currentUser: [username: loggedInUsername]]

		when: 'the request is GET'
			controller.request.method = 'GET'

		and: 'ID is "current"'
			controller.params.id = 'current'

		and: 'the controller is invoked'
			def model = controller.find()

		then: 'model.username is the current user'
			model.username == loggedInUsername

		and: 'the GithubService is queried for the logged in user'
			1 * controller.githubService.findRepositoriesByUsername(loggedInUsername)
	}

	def "when the githubId is POST'd, the username is set accordingly"() {
		given: 'a username'
			def username = 'someUser'

		when: 'the request is POST'
			controller.request.method = 'POST'

		and: 'the parameter is set'
			controller.params.githubId = username

		and: 'the controller is invoked'
			def model = controller.find()

		then: 'the model has username'
			model.username == username
	}

	def "when the githubId is POST'd, the username is used to lookup repositories"() {
		given: 'a username and corresponding repositories'
			def queryUsername = 'queryUserName'
			def repos = [name: 'name', id: 'id']
			1 * controller.githubService.findRepositoriesByUsername(queryUsername) >> repos

		when: 'the request is POST'
			controller.request.method = 'POST'

		and: 'the username is in the POST data'
			controller.params.githubId = queryUsername

		and: 'the controller is invoked'
			def model = controller.find()

		then: 'model.repositories contains the object returned from the GithubService'
			model.repositories == repos
	}

	def "when an empty githubId is POST'd, no repositories are found"() {
		given: 'the github username is empty'
			def emptyUsername = ''
			def repos = null
			1 * controller.githubService.findRepositoriesByUsername(emptyUsername) >> repos

		when: 'the request is POST'
			controller.request.method = 'POST'

		and: 'the empty username is in the POST data'
			controller.params.githubId = emptyUsername

		and: 'the controller is invoked'
			def model = controller.find()

		then: 'model.repositories is null'
			model.repositories == null
	}
}
