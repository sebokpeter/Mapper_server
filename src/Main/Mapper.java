package Main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import Communication.Server;
import Display.*;

public class Mapper {
	 public static void main(String[] arguments) {
		 Grid map = new Grid();
		 GridDisplay display = map.getGridDisplay();
		 final Server server = new Server(display);
		 
		 // Add a listener to the stop button 
		 // Use it to stop the server if clicked
		 map.btnNewButton.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent arg0) {
         		server.stop();
         	}
         });

		 
		 try {
			server.start();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// Remote connection closed
			System.exit(0);
		}
		 
	 }
}
