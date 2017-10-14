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
		
		//REmove...............................................................dsadsadsaasdhdasjkdashjkasgask
		updatedString[0]=((String[])arg1)[0];
		updatedString[1]=((String[])arg1)[1];
		updatedString[2]=((String[])arg1)[2];

		//CLI or VIEW
		
		
	
		if(arg0 instanceof View){
			
			//Case of getting notify from the CLI:
			if(arg0.getClass()==CLI.class){
				//Case of Exit the program
				if(updatedString[2].toUpperCase().equals("EXIT")){
					System.out.println("Got Exit Command");
				}
				//Case of AlgoToRun:
				else{
					((MMUModel) model).setCommands(updatedString);
					model.start();
				}
			}
			//Case of GUI change - Close the program:
			else{
				//
			}
		}
		
		if(arg0 instanceof MMUModel){
			//Finished the log file creation and VIEW initialization:
			MMUView newView = (MMUView)view;
			//when finish with FUNFUN comment out:
			//newView.setParameters(arg1);
		}
		
	}

}