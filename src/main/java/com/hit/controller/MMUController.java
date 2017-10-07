package com.hit.controller;

import java.util.Observable;
import java.util.Observer;

import com.hit.model.MMUModel;
import com.hit.model.Model;
import com.hit.view.View;

public class MMUController implements Controller,Observer {
	private String[] updatedString;
	
	public MMUController(Model model,View view){
		updatedString=null;
	}
	@Override
	public void update(Observable arg0,Object arg1) {
		// TODO Auto-generated method stub
		if(arg0 instanceof View){
			updatedString=(String[])arg1;
			if(updatedString[2].toUpperCase().equals("EXIT")){
				System.out.println("Stop");
			}
			else{
				//Need to engage operation
				//Create MMUmodel
			}
			
			//if((Object[])arg1[2].toString()=="exit"){
				
			//}
			//Else{}
			//1 option need to engage algorithm.
			//2 needs to engage Exit.
		}
		else if(arg0.getClass()==MMUModel.class){
			//Case finish with that specific algorithm
			//Case
		}
		
		
		
		
		
	}

}
