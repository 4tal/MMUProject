package com.hit.view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.SystemColor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;


import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;

import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;






import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.hit.controller.MMUController;
import com.hit.driver.CLI;
import com.hit.model.MMUModel;

public class MMUView extends Observable implements View {
	private static final String CONFIG_FILE = "src/main/resources/com/hit/config/Configuration.json";
	JFrame frmMmuSimulator;
	private JTable table_1;
	private List<String> rowsFromLog=null;
	private Integer pageFaults=0;
	private Integer pageReplacements=0;
	private int capacitySize;
	private int numberOfProcesses;
	private HashSet activeProcesses=null;
	private ArrayList freeColumns=null;
	private int rowsFromLogIndex=2;
	private JEditorPane editorPane;
	private JEditorPane editorPane_1;
	private JList list;
	DefaultTableModel tableModel;
	private JTable table;
	private ArrayList freeColuumns;
	private String currentCommand=null;
	private int sizeOfPage=5;
	
	public static void main(String[] args) {
		MMUView window = new MMUView();
		window.frmMmuSimulator.setVisible(true);
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MMUView(){
		System.out.println("View Ctor started");
		initialized();

	}
	
	private void createAndShowGUI(){
		System.out.println("Window is up");
		frmMmuSimulator = new JFrame();
		frmMmuSimulator.setMinimumSize(new Dimension(600, 300));
		frmMmuSimulator.setTitle("MMU Simulator");
		frmMmuSimulator.setBounds(100, 100, 450, 300);
		frmMmuSimulator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMmuSimulator.getContentPane().setLayout(null);
		
		JPanel playAndAll = new JPanel();
		playAndAll.setBackground(UIManager.getColor("CheckBox.background"));
		playAndAll.setBounds(10, 177, 200, 73);
		frmMmuSimulator.getContentPane().add(playAndAll);
		playAndAll.setLayout(null);
		
		
		//Add items to the list............................................................................
		list = new JList();
		DefaultListModel listOfProcessesModel=new DefaultListModel();
		
		for(int i=0;i<numberOfProcesses;i++){
			listOfProcessesModel.addElement("Process "+ (i+1));
		}
		
		list.setModel(listOfProcessesModel);
		list.setVisible(true);
		
		
		//Create the action listener for this button:~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`
		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println(list.getSelectedIndices());
				updateActiveProcesses(list.getSelectedIndices());
				
				executeNextCommand();
				
				//list.getSelectionModel()
				//-1 fir each selected process.
			}

			
		});
		btnPlay.setBounds(10, 24, 67, 23);
		playAndAll.add(btnPlay);

		
		
		
		
		//Create the action listener for this button:~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`
		JButton btnNewButton = new JButton("Play All");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("In play all: ");
				executeAllCommands();
				System.out.println(list.getSelectionModel());
				
			}
		});
		btnNewButton.setBounds(109, 24, 81, 23);
		playAndAll.add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBounds(328, 120, 111, 130);
		frmMmuSimulator.getContentPane().add(panel);
		panel.setLayout(null);
		

		
		JTextPane txtpnProcesses = new JTextPane();
		txtpnProcesses.setBounds(10, 5, 91, 20);
		txtpnProcesses.setBackground(UIManager.getColor("Button.background"));
		txtpnProcesses.setText("Processes");
		panel.add(txtpnProcesses);
		
		JScrollPane scrollPane_1 = new JScrollPane(list);
		scrollPane_1.setBounds(10, 23, 91, 96);
		panel.add(scrollPane_1);
		
		
		//Panel 1 is for the page fault and page replacement:
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(328, 11, 219, 98);
		
		frmMmuSimulator.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JTextPane txtpnAmount = new JTextPane();
		txtpnAmount.setMargin(new Insets(3, 0, 3, 3));
		txtpnAmount.setBackground(UIManager.getColor("CheckBox.background"));
		txtpnAmount.setText("Page fault amount");
		txtpnAmount.setBounds(10, 24, 118, 20);
		panel_1.add(txtpnAmount);
		
		JTextPane txtpnPageReplacementAmount = new JTextPane();
		txtpnPageReplacementAmount.setMargin(new Insets(3, 0, 3, 3));
		txtpnPageReplacementAmount.setText("Page replacement amount");
		txtpnPageReplacementAmount.setBackground(SystemColor.menu);
		txtpnPageReplacementAmount.setBounds(10, 47, 138, 20);
		panel_1.add(txtpnPageReplacementAmount);
		
		
		//Update Page Fault amount:~~~~~~How to notify this component of change in state~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`
		editorPane = new JEditorPane();
		editorPane.setText(pageFaults.toString());
		editorPane.setBounds(174, 24, 21, 20);
		panel_1.add(editorPane);
		
		//Update Page replacement amount:~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`
		editorPane_1 = new JEditorPane();
		editorPane_1.setText(pageReplacements.toString());
		editorPane_1.setBounds(174, 47, 21, 20);
		
		panel_1.add(editorPane_1);


		
		String[] cols={"0","0","0","0"};
		String[][] data={{"0","0","0","0"},{"0","0","0","0"},{"0","0","0","0"},{"0","0","0","0"},{"0","0","0","0"}};
		tableModel =new DefaultTableModel(data,cols);
		table = new JTable(tableModel);
	
		for(int i=0;i<capacitySize;i++){
			freeColuumns.add(i);
		}
		
		table.setModel(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 11, 312, 86);
		frmMmuSimulator.getContentPane().add(scrollPane);
		
		
		frmMmuSimulator.setVisible(true);
	}
	
	public void setParameters(Object arg1) {
		rowsFromLog=(List<String>) arg1;
		setPageFaults(0);
		setPageReplacements(0);
		setCapacitySize(Character.getNumericValue((rowsFromLog.get(0).charAt(3))));
		setNumberOfProcesses(Character.getNumericValue((rowsFromLog.get(1).charAt(3))));
		activeProcesses=new HashSet();
		freeColumns=new ArrayList();
		
	}
	
	
	
	protected void updatePageReplacement() {
		setPageReplacements(pageReplacements+1);
		getEditorPanePageReplacement().setText(getPageReplacements().toString());
		
	}

	protected void updatePageFault() {
		setPageFaults(pageFaults+1);
		getEditorPanePageFault().setText(getPageFaults().toString());
	}

	protected void executeAllCommands() {
		for(int i=0;i<rowsFromLog.size();i++){
			executeNextCommand();
		}
	}

	protected void executeNextCommand() {
		currentCommand=new String(rowsFromLog.get(rowsFromLogIndex));
		if(currentCommand.contains("PF")){
			updatePageFault();
		}
		else if(currentCommand.contains("PR")){
			
			//Find the next GetPageIndex();
			//Find the col to replace();
			//
			/*
			updatePageReplacement();
			updateActiveProcesses(getListOfProcesses().getSelectedIndices());
			if(activeProcesses.contains(Character.getNumericValue(currentCommand.charAt(4)))){
				System.out.println("Col: "+temp);
				setTheWholeColumn(temp);
			//Switch with pageIndex();
			 */
			//Not ready yet.
		}
		else if(currentCommand.contains("GP") && true){
			//(chechIfPageInTable(Character.getNumericValue(currentCommand.charAt(4))))
			updateActiveProcesses(getListOfProcesses().getSelectedIndices());
			if(activeProcesses.contains(Character.getNumericValue(currentCommand.charAt(4)))){
				int temp=getNextFreeCol();
				//System.out.println("Col: "+temp);
				setTheWholeColumn(temp);
			}
		}
		
		
		rowsFromLogIndex++;
		
	}

	private boolean chechIfPageInTable(int numericValue) {
		Enumeration<TableColumn> tempEnum=getTable().getTableHeader().getColumnModel().getColumns();
		while(tempEnum.hasMoreElements()){
			if((int)tempEnum.nextElement().getHeaderValue()==numericValue){
				return false;
			}
		}
		return true;
	}

	private int getNextFreeCol() {
		int temp=0;
		
		if(freeColuumns.size()>0){
			temp=(int) freeColuumns.get(0);
			freeColuumns.remove(0);
		}
		
		return temp;
		
	}

	private void setTheWholeColumn(int ColToEdit) {
		ArrayList temp=getPageValues(rowsFromLogIndex);
		
		getTable().getTableHeader().getColumnModel().getColumn(ColToEdit).setHeaderValue(getPageNumberOnGP());
		table.getTableHeader().repaint();
		
		for(int i=0;i<sizeOfPage;i++){
			tableModel.setValueAt(temp.get(i).toString(),i, ColToEdit);
		}
		table.setModel(tableModel);
		table.repaint();
	}

	private String getPageNumberOnGP() {
		return (rowsFromLog.get(rowsFromLogIndex).substring(5, rowsFromLog.get(rowsFromLogIndex).indexOf("[")).replace(" ",""));
	}

	private ArrayList getPageValues(int commandIndex) {
		ArrayList returnedArray=new ArrayList();
		String[] tempString=rowsFromLog.get(commandIndex).substring(9).replace("]","").replace(",","").split(" ");
		for(int i=0;i<sizeOfPage;i++){
			returnedArray.add(Integer.parseInt(tempString[i]));
		}
		return (returnedArray);
	}

	protected void updateActiveProcesses(int[] active) {
		activeProcesses.clear();
		for (int i=0;i<active.length;i++) {
			activeProcesses.add(active[i]+1);
		}
		//System.out.println(activeProcesses);
	}
	protected JEditorPane getEditorPanePageFault() {
		return editorPane;
	}
	protected JEditorPane getEditorPanePageReplacement() {
		return editorPane_1;
	}
	public JList getListOfProcesses() {
		return list;
	}
	protected JTable getTable() {
		return table;
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public void initialized(){
		openWindow();
	}
	
	private void openWindow() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				
				createAndShowGUI();
			}

			
		});
	}
	
	@Override
	public void start() {
		openWindow();
		
	}

	public Integer getPageFaults() {
		return pageFaults;
	}

	public void setPageFaults(int pageFaults) {
		this.pageFaults = pageFaults;
	}

	public Integer getPageReplacements() {
		return pageReplacements;
	}

	public void setPageReplacements(int pageReplacements) {
		this.pageReplacements = pageReplacements;
	}

	public int getCapacitySize() {
		return capacitySize;
	}

	public void setCapacitySize(int capacitySize) {
		this.capacitySize = capacitySize;
	}

	public int getNumberOfProcesses() {
		return numberOfProcesses;
	}

	public void setNumberOfProcesses(int numberOfProcesses) {
		this.numberOfProcesses = numberOfProcesses;
	}

}
