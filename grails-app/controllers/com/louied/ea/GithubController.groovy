package com.louied.ea

import grails.plugin.springsecurity.annotation.Secured;

@Secured(['ROLE_USER'])
class GithubController {

	static defaultAction = 'find'

	def githubService
	def springSecurityService

	def find() {
		if (!request.post && params.id != 'current') {
			return
		}

		def username
		if (params.id) {
			username = springSecurityService.currentUser.username
		} else {
			username = params.githubId
		}

		def repositories = githubService.findRepositoriesByUsername(username)
		[username: username, repositories: repositories]
	}
}
