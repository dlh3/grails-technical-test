package com.louied.ea

import grails.plugin.springsecurity.annotation.Secured;

@Secured(['ROLE_USER'])
class GithubController {

	static defaultAction = 'find'

	def find() {
		if (!request.post) {
			return
		}
		def username = params.githubId

		def repositories = null
		[username: username, repositories: repositories]
	}
}
