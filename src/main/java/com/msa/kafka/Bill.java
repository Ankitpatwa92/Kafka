package com.msa.kafka;

public class Bill {

	enum Category
	{
		dryfruit,
		cloathing;
	}
	
	Category category;
	
	enum Item {		
		kaju,
		badam,
		kissmiss,
		jeans,
		shirt,
		tshirt;
	}
	
	Item item;
	
	int total;
	
	String description;
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	
	
}
