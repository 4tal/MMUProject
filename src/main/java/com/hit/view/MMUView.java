package com.hit.view;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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

public class MMUView extends Observable implements View {
	JFrame frmMmuSimulator;
	private JTable table_1;
	private List<String> rowsFromLog=null;
	private List<String> getPagesFromLogList=null;
	private List<String> otherCommandFromLogList=null;
	
	
	private Integer pageFaults=0;
	private Integer pageReplacements=0;
	private int capacitySize;
	private int numberOfProcesses;
	private HashSet activeProcesses=null;
	private ArrayList freeColumns=null;
	
	

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
	
	private void createAndShowGUI(){
		frmMmuSimulator = new JFrame();
		frmMmuSimulator.setMinimumSize(new Dimension(600, 300));
		frmMmuSimulator.setTitle("MMU Simulator");
		frmMmuSimulator.setBounds(100, 100, 450, 300);
		frmMmuSimulator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMmuSimulator.getContentPane().setLayout(null);
		frmMmuSimulator.setResizable(false);
		
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
				updateActiveProcesses(list.getSelectedIndices());
				//executeNextCommand();
			}

			
		});
		btnPlay.setBounds(10, 24, 67, 23);
		playAndAll.add(btnPlay);

		
		
		
		
		//Create the action listener for this button:~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`
		JButton btnNewButton = new JButton("Play All");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("In play all: ");
				//executeAllCommands();
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
			freeColumns.add(i);
		}
		
		table.setModel(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 11, 312, 86);
		
		frmMmuSimulator.getContentPane().add(scrollPane);
		frmMmuSimulator.setVisible(true);
		System.out.println(rowsFromLog);
	}
	
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
	
	protected void updateActiveProcesses(int[] active) {
		activeProcesses.clear();
		for (int i=0;i<active.length;i++) {
			activeProcesses.add(active[i]+1);
		}
		
	}
	
	private void getAllPagesFromLog() {
		for(int i = 2; i < rowsFromLog.size(); i++) {
			if(validateCommand("GP", i)) {
				String[] fragments = rowsFromLog.get(i).split(" ");
				String procId =fragments[0].substring(4);
				String pageId= fragments[1];
				int startIdx = rowsFromLog.get(i).indexOf("[") + 1;
	            int endIdx = rowsFromLog.get(i).indexOf("]");
	            String numbersArray = rowsFromLog.get(i).substring(startIdx, endIdx).replace(",","");
	            //String[] numbers = numbersArray.split(",");
				//allGetPagesFromRam.put(procId+" "+pageId, numbers);
	            getPagesFromLogList.add(procId+" "+pageId+" "+numbersArray);
			}
			else{
				otherCommandFromLogList.add(rowsFromLog.get(i));
			}
		}
		
		
		
		/*for(int i=0;i<getPagesCommands.size();i++){
			System.out.println(getPagesCommands.get(i));
		}*/
		
		
//		for(Map.Entry<String, String[]> entry: allGetPagesFromRam.entrySet()) {
//			System.out.println("key " + entry.getKey());
//			for(int i = 0; i < entry.getValue().length; i++) {
//				System.out.print(entry.getValue()[i]);
//			}
/*//			System.out.println();
//		}
		System.out.println(otherCommands);*/
		
	}
	private boolean validateCommand(String command, int lineNumber) {
        return rowsFromLog.get(lineNumber).contains(command);
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
}
