package grailsproject

import com.inventory.User
import com.inventory.Role
import com.inventory.UserRole
import grails.plugin.springsecurity.SpringSecurityService
import groovy.sql.Sql

import javax.sql.DataSource

class BootStrap {

    DataSource dataSource
    SpringSecurityService springSecurityService

    def init = { servletContext ->
        Role.withTransaction { status ->
            def adminRole = Role.findByAuthority('ROLE_ADMIN') ?: new Role(authority: 'ROLE_ADMIN').save(flush: true)
            def userRole  = Role.findByAuthority('ROLE_USER')  ?: new Role(authority: 'ROLE_USER').save(flush: true)

            if (!User.findByUsername('admin')) {
                def admin = new User(username: 'admin', password: springSecurityService.encodePassword('DevPass123!'))
                        .save(flush: true, failOnError: true)
                UserRole.create(admin, adminRole, true)
            }

            if (!User.findByUsername('user')) {
                def demoUser = new User(username: 'user', password: springSecurityService.encodePassword('DevPass123!'))
                        .save(flush: true, failOnError: true)
                UserRole.create(demoUser, userRole, true)
            }
        }

        if (isH2()) {
            registerH2StockFunctions()
        }
    }

    private boolean isH2() {
        def conn = dataSource.connection
        try {
            return conn.metaData.databaseProductName?.toUpperCase()?.contains('H2')
        } finally {
            conn.close()
        }
    }

    private void registerH2StockFunctions() {
        def sql = new Sql(dataSource)
        try {
            sql.execute('CREATE SCHEMA IF NOT EXISTS dbo')
            sql.execute('''
                CREATE ALIAS IF NOT EXISTS dbo.GetCurrentStock
                FOR "com.inventory.support.H2StockFunctions.getCurrentStock"
            ''')
            sql.execute('''
                CREATE ALIAS IF NOT EXISTS dbo.ProcessRestock
                FOR "com.inventory.support.H2StockFunctions.processRestock"
            ''')
        } finally {
            sql.close()
        }
    }

    def destroy = {
    }
}