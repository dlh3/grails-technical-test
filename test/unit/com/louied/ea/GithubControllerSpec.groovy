package com.louied.ea;

@TestFor(GithubController)
class GithubControllerSpec extends spock.lang.Specification {

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
}
