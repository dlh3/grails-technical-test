package com.louied.ea

import grails.plugin.springsecurity.annotation.Secured;

@Secured(['ROLE_USER'])
class GithubController {

	static defaultAction = 'find'

	def githubService

	def find() {
		if (!request.post) {
			return
		}
		def username = params.githubId

		def repositories = githubService.findRepositoriesByUsername(username)
		[username: username, repositories: repositories]
	}
}
