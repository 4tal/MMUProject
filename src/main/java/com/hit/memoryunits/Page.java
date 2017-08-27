package com.hit.memoryunits;


public class Page<T> extends java.lang.Object implements java.io.Serializable {
	T m_Content;
	java.lang.Long m_long;
	java.lang.String m_string;
	
	
	
	public Page(java.lang.Long id, T content){
		
	}
	
	public boolean equals(java.lang.Object obj){
		return true;
	}
	
	
	public java.lang.Long getPageId(){
		return m_long;
	}
	
	public int hashCode(){
		return 0;
	}
	
	public void setContent(T content){
		
	}
	
	public T getContent(){
		return m_Content;
	}
	
	public void setPageId(Long pageId){
		
	}
	
	public java.lang.String toString(){
		return m_string;
	}

}
