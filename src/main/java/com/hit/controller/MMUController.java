package com.hit.controller;

import java.util.Observable;
import java.util.Observer;

import com.hit.driver.CLI;
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

		//CLI or VIEW
		if(arg0 instanceof View){
			
			//Case of getting notify from the CLI:
			if(arg0.getClass()==CLI.class){
				if(updatedString[2].toUpperCase().equals("EXIT")){
					System.out.println("Got Exit Command");
				}
				//Other command to execute:
				else{
					//System.out.println("3");
					((MMUModel) model).setCommands(updatedString);
					//System.out.println("7");
					
					
					model.start();//Add the command string
					
					
					//System.out.println("8");
				}
			}
			//Case of GUI change:
			else{
				
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