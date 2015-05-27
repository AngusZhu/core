package com.colverframework.core.sample.model;

import com.colverframework.core.sample.base.BaseModel;

public class Member extends BaseModel{

    /**
     * 
     */
    private static final long serialVersionUID = -1959528436584592183L;
    
    
    private String id;
    private String name;
    
    public Member(){}
    
    public Member(String id, String nickname){
        this.setId(id);
        this.setName(nickname);
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    
}