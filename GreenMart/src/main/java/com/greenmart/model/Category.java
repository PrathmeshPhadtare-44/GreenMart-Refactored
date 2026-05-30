package com.greenmart.model;

import lombok.Data;

@Data
public class Category {
	private int categoryId;
    private String categoryName;
    private int status;
    public Category(String categoryName, int status) {
        this.categoryName = categoryName;
        this.status = status;
    }
    public Category() {}
}
