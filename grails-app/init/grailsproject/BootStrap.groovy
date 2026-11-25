import com.inventory.User
import com.inventory.Role
import com.inventory.UserRole

class BootStrap {

    def init = { servletContext ->
        def adminRole = Role.findByAuthority('ROLE_ADMIN') ?: new Role(authority: 'ROLE_ADMIN').save(flush: true)
        def userRole  = Role.findByAuthority('ROLE_USER')  ?: new Role(authority: 'ROLE_USER').save(flush: true)

        if (!User.findByUsername('admin')) {
            def admin = new User(username: 'admin', password: 'DevPass123!').save(flush: true)
            UserRole.create(admin, adminRole, true)
        }
    }

    def destroy = {
    }
}