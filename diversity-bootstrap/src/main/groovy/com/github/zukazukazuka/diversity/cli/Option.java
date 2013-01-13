package com.github.zukazukazuka.diversity.cli;

public class Option {
	
    private String name;
    
    private String description;

    public Option(String name, String description) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("illegal option specified");
        }

        this.name = name;
        this.description = description != null ? description : "";
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
