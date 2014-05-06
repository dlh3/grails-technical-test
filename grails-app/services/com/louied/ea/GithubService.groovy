package com.louied.ea

import grails.converters.JSON

/**
 * Service to provide integration to github API.
 */
class GithubService {

	def apiHost = 'https://api.github.com/'

    def findRepositoriesByUsername(username) {
		if (!username) {
			return
		}

		try{
			JSON.parse(getGithubUrlForPath('users', username, 'repos').text)
		} catch (FileNotFoundException ex) {
			// user not found
			return
		}
    }

	def getGithubUrlForPath(String ... pathParts) {
		"${apiHost}${pathParts.join('/')}".toURL()
	}
}
