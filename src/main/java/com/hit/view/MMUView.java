package com.hit.view;

import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.UIManager;




	
public class MMUView extends Observable implements View {
	JFrame frmMmuSimulator;
	private JTable table_1;
	boolean notInit = true;
	/**
	 * @wbp.parser.entryPoint
	 */
	public MMUView(){
		System.out.println("View Started");
		//need to make this command to active:
		//notInit=false;
		//start();
	}
	

	//Need to update in everyOneOfbutton pressed:
	
	private void createAndShowGUI() {
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
		
		JButton btnPlay = new JButton("Play");
		btnPlay.setBounds(10, 24, 67, 23);
		playAndAll.add(btnPlay);
		
		JButton btnNewButton = new JButton("Play All");
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
		
		JList list = new JList();
		list.setBounds(28, 103, 54, -78);
		panel.add(list);
		
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
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setText("3");
		editorPane.setBounds(174, 24, 16, 20);
		panel_1.add(editorPane);
		
		JEditorPane editorPane_1 = new JEditorPane();
		editorPane_1.setText("3");
		editorPane_1.setBounds(174, 47, 16, 20);
		panel_1.add(editorPane_1);
		
		JPanel table = new JPanel();
		table.setBounds(0, 0, 359, 117);
		frmMmuSimulator.getContentPane().add(table);
		
		table_1 = new JTable();
		table_1.setToolTipText("");
		table_1.setBackground(UIManager.getColor("Button.foreground"));
		table.add(table_1);
		
		frmMmuSimulator.setVisible(true);
		
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
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void start() { 
		if(!notInit)
			openWindow();
		else {
			frmMmuSimulator.pack();
			frmMmuSimulator.setVisible(true);
		}
	}
	
}
