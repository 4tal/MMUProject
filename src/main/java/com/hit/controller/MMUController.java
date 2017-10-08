package com.hit.controller;

import java.util.Observable;
import java.util.Observer;

import com.hit.model.MMUModel;
import com.hit.model.Model;
import com.hit.view.View;

public class MMUController implements Controller,Observer {
	private String[] updatedString;
	private Model model;
	private View view;
	
	public MMUController(Model inputModel,View inputView){
		updatedString=new String[3];
		model=inputModel;
		view=inputView;
	}
	@Override
	public void update(Observable arg0,Object arg1) {
		// TODO Auto-generated method stub
		updatedString[0]=((String[])arg1)[0];
		updatedString[1]=((String[])arg1)[1];
		updatedString[2]=((String[])arg1)[2];
		if(arg0 instanceof View){
			//System.out.println("1");
			if(updatedString[2].toUpperCase().equals("EXIT")){
				//System.out.println("2");
			}
			else{
				//System.out.println("3");
				((MMUModel) model).setCommands(updatedString);
				//System.out.println("7");
				model.start();
				//System.out.println("8");
				
			}
			//System.out.println("5");
			//if((Object[])arg1[2].toString()=="exit"){
				
			//}
			//Else{}
			//1 option need to engage algorithm.
			//2 needs to engage Exit.
		}
		else if(arg0.getClass()==MMUModel.class){
			//System.out.println("4");
			//Case finish with that specific algorithm
			//Case
		}
		
		
		
		
		
	}

}
