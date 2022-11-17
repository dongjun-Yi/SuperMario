package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;

import controller.Controller;
import controller.PlayerController;
import main.GamePanel;
import server.GameModelMsg;
import server.NetworkStatus;
import view.StartScreenView;

public class GameClient {
	private String userName;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Socket socket;
	private ListenNetwork net;
	private GamePanel gamePanel = GamePanel.getInstance();
	

	public GameClient(String username, String ip_addr, String port_no) {
		userName = username;
		try {
			socket = new Socket(ip_addr, Integer.parseInt(port_no));
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());

			GameModelMsg objectGameMsg = new GameModelMsg(username, NetworkStatus.LOG_IN);
			SendObject(objectGameMsg);

			net = new ListenNetwork();
			net.start();

		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class ListenNetwork extends Thread {
		public void run() {
			while (true) {
				try {
					
					Object obgm = null;
					GameModelMsg objectGameMsg;
					try {
						obgm = ois.readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						break;
					}
					if (obgm == null)
						break;
					if (obgm instanceof GameModelMsg) {
						objectGameMsg = (GameModelMsg) obgm;
					} else
						continue;
					//System.out.println(objectGameMsg.getCode());
					if(objectGameMsg.getCode().matches(NetworkStatus.LOG_IN)) { //서버에 접속하고 
						gamePanel.setGameStatus("startScreen");
					}
					if(objectGameMsg.getCode().matches(NetworkStatus.GAME_START)) { //400
						gamePanel.setGameStatus("gameRunning");
					}
					if(objectGameMsg.getCode().matches(NetworkStatus.GAME_BUTTON)) {
						System.out.println("button");
					}
				} catch (IOException e) {
					try {
						ois.close();
						oos.close();
						socket.close();

						break;
					} catch (Exception ee) {
						break;
					} // catch문 끝
				} // 바깥 catch문끝

			}
		}
	}

	public void SendObject(GameModelMsg objectGameMsg) {
		try {
			oos.writeObject(objectGameMsg);
		} catch (IOException e) {

		}
	}
	
	public void SendButtonAction(double x, double y) {
		GameModelMsg objectGameMsg = new GameModelMsg(userName, NetworkStatus.GAME_BUTTON);
		objectGameMsg.setX(x);
		objectGameMsg.setY(y);
		SendObject(objectGameMsg);
	}
}
