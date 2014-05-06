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

	def 'the model is empty on GET'() {
		when: 'the request is GET'
			controller.request.method = 'GET'

		and: 'the controller is invoked'
			def model = controller.find()

		then: 'there is no model returned'
			model == null
	}

	def 'the GithubService is not queried on GET'() {
		when: 'the request is GET'
			controller.request.method = 'GET'

		and: 'the controller is invoked'
			def model = controller.find()

		then: 'the service is not queried'
			0 * controller.githubService.findRepositoriesByUsername(_)
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
