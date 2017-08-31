package com.hit.memoryunits;


public class Page<T> implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private T content;
	private Long id;


	public Page(java.lang.Long id, T content){
		this.setId(id);
		this.setContent(content);
	}

	@Override
	public boolean equals(Object obj){
		if(this == obj) 
		{
			return true;
		} 
		return false;

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
