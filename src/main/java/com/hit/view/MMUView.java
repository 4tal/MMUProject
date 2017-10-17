package com.hit.view;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;

import javax.imageio.ImageIO;
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

public class MMUView extends Observable implements View {
	JFrame frmMmuSimulator;
	private List<String> rowsFromLog=null;
	private List<String> getPagesFromLogList=null;
	private List<String> otherCommandFromLogList=null;
	
	
	private Integer pageFaults=0;
	private Integer pageReplacements=0;
	private int capacitySize;
	private int numberOfProcesses;
	private HashSet activeProcesses=null;
	private ArrayList freeColumns=null;
	private int currCommand=0;
	
	

	private JEditorPane editorPane;
	private JEditorPane editorPane_1;
	private JList list;
	DefaultTableModel tableModel;
	private JTable table;
	private String[] cols;
	private String[][] data;
	
	public MMUView(){
		activeProcesses=new HashSet();
		freeColumns=new ArrayList();
	}
	
	/**
	 * initialize the components and show the view
	 */
	private void createAndShowGUI(){
		frmMmuSimulator = new JFrame();
		frmMmuSimulator.setMinimumSize(new Dimension(600, 300));
		frmMmuSimulator.setTitle("MMU Simulator");
		frmMmuSimulator.setBounds(100, 100, 450, 300);
		frmMmuSimulator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMmuSimulator.getContentPane().setLayout(null);
		frmMmuSimulator.setResizable(false);
		try {
			frmMmuSimulator.setIconImage(ImageIO.read(new File("resources\\ram.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		JPanel playAndAll = new JPanel();
		playAndAll.setBackground(UIManager.getColor("CheckBox.background"));
		playAndAll.setBounds(10, 177, 200, 73);
		frmMmuSimulator.getContentPane().add(playAndAll);
		playAndAll.setLayout(null);
		
		list = new JList();
		DefaultListModel listOfProcessesModel=new DefaultListModel();
		
		for(int i=0;i<numberOfProcesses;i++){
			listOfProcessesModel.addElement("Process "+ (i+1));
		}
		
		list.setModel(listOfProcessesModel);
		list.setVisible(true);
		
		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {	
				updateActiveProcesses(list.getSelectedIndices());
				executeNextCommand();
			}	
		});
		btnPlay.setBounds(10, 24, 67, 23);
		playAndAll.add(btnPlay);
		JButton btnNewButton = new JButton("Play All");
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				executeAllCommands();
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
		
		
		//Update Page Fault amount:
		editorPane = new JEditorPane();
		editorPane.setText(pageFaults.toString());
		editorPane.setBounds(174, 24, 21, 20);
		panel_1.add(editorPane);
		
		//Update Page replacement amount:
		editorPane_1 = new JEditorPane();
		editorPane_1.setText(pageReplacements.toString());
		editorPane_1.setBounds(174, 47, 21, 20);
		
		panel_1.add(editorPane_1);


		
		String[] cols={"0","0","0","0","0","0","0","0","0","0","0","0"};
		String[][] data={{"0","0","0","0","0","0","0","0","0","0","0","0"},{"0","0","0","0","0","0","0","0","0","0","0","0"},{"0","0","0","0","0","0","0","0","0","0","0","0"},{"0","0","0","0","0","0","0","0","0","0","0","0"},{"0","0","0","0","0","0","0","0","0","0","0","0"}};
		tableModel =new DefaultTableModel(data,cols);
		table = new JTable(tableModel);
	
		for(int i=0;i<capacitySize;i++){
			freeColumns.add(i);
		}
		
		table.setModel(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 11, 312, 86);
		
		frmMmuSimulator.getContentPane().add(scrollPane);
		frmMmuSimulator.setVisible(true);
	}
	
	/**
	 * 
	 * @param arg1 set parameters to show in the view
	 */
	@SuppressWarnings("unchecked")
	public void setParameters(Object arg1) {
		otherCommandFromLogList=new ArrayList<>();
		getPagesFromLogList=new ArrayList<>();
		rowsFromLog=(List<String>) arg1;
		setPageFaults(0);
		setPageReplacements(0);
		getAllPagesFromLog();
		setCapacitySize(Integer.parseInt(rowsFromLog.get(0).substring(3)));
		setNumberOfProcesses(Integer.parseInt(rowsFromLog.get(1).substring(3)));
	}
	
	/**
	 * 
	 * @param active the id's of the active/selected processes
	 */
	protected void updateActiveProcesses(int[] active) {
		activeProcesses.clear();
		for (int i=0;i<active.length;i++) {
			activeProcesses.add(active[i]+1);
		}
		
	}
	
	/**
	 * read all the GP commands from log file to data structure
	 */
	private void getAllPagesFromLog() {
		for(int i = 2; i < rowsFromLog.size(); i++) {
			if(validateCommand("GP", i)) {
				String[] fragments = rowsFromLog.get(i).split(" ");
				String procId =fragments[0].substring(4);
				String pageId= fragments[1];
				int startIdx = rowsFromLog.get(i).indexOf("[") + 1;
	            int endIdx = rowsFromLog.get(i).indexOf("]");
	            String numbersArray = rowsFromLog.get(i).substring(startIdx, endIdx).replace(",","");
	            getPagesFromLogList.add(procId+" "+pageId+" "+numbersArray);
			}
			else{
				otherCommandFromLogList.add(rowsFromLog.get(i));
			}
		}
	}
	
	/**
	 * 
	 * @param command the command to check
	 * @param lineNumber the line number of the command
	 * @return indicates if the command is valid
	 */
	private boolean validateCommand(String command, int lineNumber) {
        return rowsFromLog.get(lineNumber).contains(command);
    }
	
	/**
	 * execute the next command in the log file
	 */
	protected void executeNextCommand() {
		if(currCommand<otherCommandFromLogList.size()){
			Integer tempProcessNumber=Integer.parseInt(getPagesFromLogList.get(currCommand).split(" ")[0]);
			updateActiveProcesses(getListOfProcesses().getSelectedIndices());
			if(otherCommandFromLogList.get(currCommand).contains("PF") && activeProcesses.contains(tempProcessNumber)){
				int temp=getNextFreeCol();
				setTheWholeColumn(temp);
				updatePageFault();
			}
			else if(otherCommandFromLogList.get(currCommand).contains("PR") && activeProcesses.contains(tempProcessNumber)){
				setTheWholeColumn(findColIndexByPage(Integer.parseInt(otherCommandFromLogList.get(currCommand).split(" ")[2])));
				updatePageReplacement();
			}
			currCommand++;
		}
	}
	
	/**
	 * 
	 * @param page the number(id) of the page
	 * @return the column of the page is stored
	 */
	protected int findColIndexByPage(int page){
		for(int i=0;i<capacitySize;i++){
			if(Integer.parseInt(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString())==page){
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * update the pages fault amount in the view
	 */
	protected void updatePageFault() {
		setPageFaults(getPageFaults()+1);
		getEditorPanePageFault().setText(String.valueOf(getPageFaults()));
	}
	
	/**
	 * update the page replacement amount in the view
	 */
	protected void updatePageReplacement() {
		setPageReplacements(pageReplacements+1);
		getEditorPanePageReplacement().setText(String.valueOf(getPageReplacements()));
		
	}

	/**
	 * run all the commands one after the other
	 */
	protected void executeAllCommands() {
		for(int i=0;i<rowsFromLog.size();i++){
			executeNextCommand();
		}
	}
	
	/**
	 * 
	 * @return get the next column the is not in use
	 */
	private int getNextFreeCol() {
		int temp=0;
		
		if(freeColumns.size()>0){
			temp=(int) freeColumns.get(0);
			freeColumns.remove(0);
		}
		return temp;
	}
	
	/**
	 * 
	 * @param ColToEdit the column number in the view that will be edited
	 */
	private void setTheWholeColumn(int ColToEdit) {
		table.getTableHeader().getColumnModel().getColumn(ColToEdit).setHeaderValue(getPagesFromLogList.get(currCommand).split(" ")[1]);
		table.getTableHeader().repaint();
		
		for(int i=0;i<5;i++){
			tableModel.setValueAt(getPagesFromLogList.get(currCommand).split(" ")[2+i],i, ColToEdit);
		}	
		table.setModel(tableModel);
		table.repaint();
	}
	
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
	
	/**
	 * @see View#start()
	 */
	@Override
	public void start() {
		openWindow();
		
	}

	public int getPageFaults() {
		return pageFaults;
	}

	public void setPageFaults(int pageFaults) {
		this.pageFaults = pageFaults;
	}

	public int getPageReplacements() {
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
	
	public JList getListOfProcesses() {
		return list;
	}
	protected JEditorPane getEditorPanePageFault() {
		return editorPane;
	}
	protected JEditorPane getEditorPanePageReplacement() {
		return editorPane_1;
	}
	
}
