package com.inventory

class UserRole implements Serializable {

	private static final long serialVersionUID = 1

	User user
	Role role

	boolean equals(other) {
		if (!(other instanceof UserRole)) return false
		other.user?.id == user?.id && other.role?.id == role?.id
	}

	int hashCode() {
		Objects.hash(user?.id, role?.id)
	}

	static UserRole get(long userId, long roleId) {
		UserRole.where { user { id == userId }; role { id == roleId } }.get()
	}

	static UserRole create(User user, Role role, boolean flush = false) {
		def instance = new UserRole(user: user, role: role)
		instance.save(flush: flush, insert: true)
		instance
	}

	static boolean remove(User user, Role role, boolean flush = false) {
		int rowCount = UserRole.where { user == user && role == role }.deleteAll()
		if (flush) { UserRole.withSession { it.flush() } }
		rowCount > 0
	}

	static void removeAll(User user, boolean flush = false) {
		UserRole.where { user == user }.deleteAll()
		if (flush) { UserRole.withSession { it.flush() } }
	}

	static void removeAll(Role role, boolean flush = false) {
		UserRole.where { role == role }.deleteAll()
		if (flush) { UserRole.withSession { it.flush() } }
	}

	static mapping = {
		id composite: ['user', 'role']
		version false
	}
}