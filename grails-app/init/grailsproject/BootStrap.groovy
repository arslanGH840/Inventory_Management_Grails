import com.inventory.User
import com.inventory.Role
import com.inventory.UserRole
import grails.util.Environment
import groovy.sql.Sql

import javax.sql.DataSource

class BootStrap {

    DataSource dataSource

    def init = { servletContext ->
        def adminRole = Role.findByAuthority('ROLE_ADMIN') ?: new Role(authority: 'ROLE_ADMIN').save(flush: true)
        def userRole  = Role.findByAuthority('ROLE_USER')  ?: new Role(authority: 'ROLE_USER').save(flush: true)

        if (!User.findByUsername('admin')) {
            def admin = new User(username: 'admin', password: 'DevPass123!').save(flush: true)
            UserRole.create(admin, adminRole, true)
        }

        if (Environment.current == Environment.TEST) {
            registerH2StockFunctions()
        }
    }

    private void registerH2StockFunctions() {
        def sql = new Sql(dataSource)
        try {
            sql.execute('CREATE SCHEMA IF NOT EXISTS dbo')
            sql.execute('''
                CREATE ALIAS IF NOT EXISTS "dbo"."GetCurrentStock"
                FOR "com.inventory.support.H2StockFunctions.getCurrentStock"
            ''')
            sql.execute('''
                CREATE ALIAS IF NOT EXISTS "dbo"."ProcessRestock"
                FOR "com.inventory.support.H2StockFunctions.processRestock"
            ''')
        } finally {
            sql.close()
        }
    }

    def destroy = {
    }
}