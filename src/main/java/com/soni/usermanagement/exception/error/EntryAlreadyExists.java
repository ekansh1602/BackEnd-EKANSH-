package com.soni.usermanagement.exception.error;

public class EntryAlreadyExists extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EntryAlreadyExists(String id, String name) {
        super("Entry already exists: " + id +" (" + name + ")");
    }

	public EntryAlreadyExists(String email) {
		// TODO Auto-generated constructor stub
		super("Entry already exists: " + " (" + email + ")");
	}
}