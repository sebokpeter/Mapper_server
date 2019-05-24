package Communication;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import Display.GridDisplay;
import Movement.RobotPosition;

/**
 * This class is responsible for listening to the robot
 * @author sebok
 *
 */
public class Server {

	private boolean done = false;
	private GridDisplay gd;
	
	
	public Server(GridDisplay g) {
		this.gd = g;
	}
	
	public void start() throws IOException, ClassNotFoundException, InterruptedException {
		ServerSocket serverSocket = new ServerSocket(5000);
		System.out.println("Waiting for client connection...");
		Socket s = serverSocket.accept();
		System.out.println("Client connected.");
		
		// DataInputStream dis = new DataInputStream(s.getInputStream());
		// DataOutputStream dos = new DataOutputStream(s.getOutputStream());
		
		ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
		
		while(!done) { 
			RobotPosition position = (RobotPosition) ois.readObject();	
			System.out.println(String.format("Recieved new position data: x: %f, y: %f", position.getX(), position.getY()));
			gd.addPosition(position);
		}
		
		serverSocket.close();
	}

	public void stop() {
		System.out.println("Closing server");
		this.done = true;
	}

}
