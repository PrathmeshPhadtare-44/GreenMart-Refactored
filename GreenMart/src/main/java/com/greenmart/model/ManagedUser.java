package com.greenmart.model;

import lombok.Data;

@Data
public class ManagedUser {
	 private int userId;
	    private String userName;
	    private String email;
	    private String role;
	    private int status;
}
