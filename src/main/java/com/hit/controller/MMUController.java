package com.hit.controller;

import java.util.Observable;
import java.util.Observer;

import com.hit.driver.CLI;
import com.hit.model.MMUModel;
import com.hit.model.Model;
import com.hit.view.MMUView;
import com.hit.view.View;

public class MMUController implements Controller,Observer {
	private String[] updatedString;
	private Model model;
	private View view;
	private CLI cliObjJustForHash;
	
	public MMUController(Model inputModel,View inputView){
		updatedString=new String[3];
		model=inputModel;
		view=inputView;
		
	}
	@Override
	public void update(Observable arg0,Object arg1) {
		// TODO Auto-generated method stub
		
		
		if(arg0 instanceof View){
			
			//Case of getting notify from the CLI:
			if(arg0.getClass()==CLI.class){
				//Case of Exit the program
				if(((String[])arg1)[2].toUpperCase().equals("EXIT")){
					System.out.println("Got Exit Command");
				}
				//Case of AlgoToRun:
				else{
					((MMUModel) model).setCommands((String[])arg1);
					model.start();
				}
			}
			//Case of GUI change - Close the program:
			else{
				//
			}
		}
		if(arg0 instanceof MMUModel){
			MMUView newView = (MMUView)view;
			newView.setParameters(arg1);
			newView.start();
		}
	}
}