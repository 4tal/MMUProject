package com.hit.memoryunits;


public class Page<T> implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private T content;
	private Long id;


	/**
	 *
	 * @param id the id of the page.
	 * @param content
	 */
	public Page(Long id, T content){
		this.setId(id);
		this.setContent(content);
	}

	public Page() {
	}

	/**
	 *
	 * @param obj the object to compare
	 * @return indicates if the 2 object are the same instance or have the same id
	 */
	@Override
	public boolean equals(Object obj){
		if(this == obj) 
		{
			return true;
		}

		if(!(obj instanceof Page)){
			return false;
		}

		Page page = (Page) obj;

		return page.getPageId() == this.id;
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
