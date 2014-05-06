import grails.util.Environment;

import com.louied.ea.Role
import com.louied.ea.User
import com.louied.ea.UserRole

class BootStrap {

    def init = { servletContext ->
        def currentEnv = Environment.current
		
		def dev = {
	        def admin = new User(username: 'admin', password: 'admin').save();
	        def user = new User(username: 'user', password: 'user').save();
	        
	        def roleAdmin = new Role(authority: 'ROLE_ADMIN').save();
	        def roleUser = new Role(authority: 'ROLE_USER').save();
	        
	        UserRole.create(admin, roleAdmin);
	        UserRole.create(admin, roleUser);
	        UserRole.create(user, roleUser);
        }

		def test = {}

		def prod = {
			def admin = new User(username: 'admin', password: 'free2play').save();
			def user = new User(username: 'dlh3', password: 'dlh3').save();
			
			def roleAdmin = new Role(authority: 'ROLE_ADMIN').save();
			def roleUser = new Role(authority: 'ROLE_USER').save();
			
			UserRole.create(admin, roleAdmin);
			UserRole.create(admin, roleUser);
			UserRole.create(user, roleUser);
		}
		
		switch (currentEnv) {
			case Environment.DEVELOPMENT:
				dev();
				break;
			case Environment.TEST:
				test();
				break;
			case Environment.PRODUCTION:
			default:
				prod();
				break;
        }
    }
    def destroy = {
    }
}
