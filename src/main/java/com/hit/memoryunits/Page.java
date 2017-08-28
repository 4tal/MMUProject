package com.hit.memoryunits;


import java.io.Serializable;

public class Page<T> implements Serializable {
	private T content;
	private Long id;
	private String string;


	public Page(Long id, T content){
		this.setId(id);
		this.setContent(content);
	}

	@Override
	public boolean equals(Object obj){
		if(this == obj) {
			return true;
		} else if(!(obj instanceof Page)) {
			return false;
		}

		Page page = (Page) obj;

		return page.getPageId() == id;
	}


	
	public Long getPageId(){
		return id;
	}

	@Override
	public int hashCode(){
		return id.intValue();
	}
	
	public void setContent(T content){
		this.content = content;
	}
	
	public T getContent(){
		return content;
	}
	
	public void setId(Long id){
		this.id = id;
	}

	@Override
	public String toString() {
		return "(" + id + "," + content.toString() + ")";
	}
}
