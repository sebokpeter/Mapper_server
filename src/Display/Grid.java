package Display;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Grid {
	 private GridDisplay gd = new GridDisplay();
	 public JButton btnNewButton = new JButton("Stop");
	 
	 public GridDisplay getGridDisplay() {
		return this.gd;
	}
	 
	 public Grid() {		 
	        EventQueue.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                try {
	                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
	                    ex.printStackTrace();
	                }

	                JFrame frame = new JFrame("Map");
	                frame.setResizable(false);
	                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	                
	                JSplitPane splitPane = new JSplitPane();
	                splitPane.setEnabled(false);
	                splitPane.setResizeWeight(0.5);
	                splitPane.setContinuousLayout(true);
	                frame.getContentPane().add(splitPane, BorderLayout.CENTER);
	                
	                GridDisplay gridDisplay = gd;	            
	                splitPane.setLeftComponent(gridDisplay);
	                
	                JPanel panel = new JPanel();
	                splitPane.setRightComponent(panel);
	                panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	                
	                JLabel lblNewLabel_1 = new JLabel("Stop the server:");
	                panel.add(lblNewLabel_1);
	               
	                panel.add(btnNewButton);
	                frame.pack();
	                frame.setLocationRelativeTo(null);
	                frame.setVisible(true);
	            }
	        });
	    }
}  

