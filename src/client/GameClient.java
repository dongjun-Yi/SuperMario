package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import controller.OthersController;
import main.GamePanel;
import server.GameModelMsg;
import server.NetworkStatus;

public class GameClient {
	private String userName;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Socket socket;
	private ListenNetwork net;
	private GamePanel gamePanel = GamePanel.getInstance();
	private OthersController otherController = (OthersController) gamePanel.getOthersController();
	String roomList[] = null;
	private String roomNumber = "";

	public String getRoomNumber() {
		return roomNumber;
	}

	public GameClient(String username, String ip_addr, String port_no) {
		try {
			socket = new Socket(ip_addr, Integer.parseInt(port_no));
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());

			SendLoginMessage(username);

			net = new ListenNetwork();
			net.start();

		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void threadInterrupt() {
		net.interrupt();
	}

	public String getUserName() {
		return userName;
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
					// System.out.println(objectGameMsg.getCode());
					if (objectGameMsg.getCode().matches(NetworkStatus.LOG_IN)) { // 400
						userName = objectGameMsg.getPlayerName();
					}
					if (objectGameMsg.getCode().matches(NetworkStatus.SHOW_LIST)) { // 1000
						if (objectGameMsg.getRoomList().matches("")) {
							gamePanel.gameRoomMake(null);
						}
						roomList = objectGameMsg.getRoomList().split(" ");
						gamePanel.gameRoomMake(roomList);
					}
					if (objectGameMsg.getCode().matches(NetworkStatus.GAME_READY)) { // 400
						gamePanel.gameReady();
					}
					if (objectGameMsg.getCode().matches(NetworkStatus.GAME_START)) { // 400
						roomNumber = objectGameMsg.getRoomNumber();
						gamePanel.setPlayerNumber(objectGameMsg.getPlayerNum());
						gamePanel.gameRunning();
					}
					if (objectGameMsg.getCode().matches(NetworkStatus.GAME_BUTTON)) {
						// 좌표 동기화
						otherController.getPlayer().setX(objectGameMsg.getX());
						otherController.getPlayer().setY(objectGameMsg.getY());
						// 속도 동기화
						// otherController.getPlayer().setxLeftVel(objectGameMsg.getxLeftVel());
						// otherController.getPlayer().setxRightVel(objectGameMsg.getxRightVel());
						// otherController.getPlayer().setyVel(objectGameMsg.getyVel());
						// 키 동기화
						otherController.setKeyPressed(objectGameMsg.isUpPressed(), objectGameMsg.isDownPressed(),
								objectGameMsg.isLeftPressed(), objectGameMsg.isRightPressed(),
								objectGameMsg.isSpacePressed());
					}
					if (objectGameMsg.getCode().matches(NetworkStatus.GAME_LOSE)) {
						gamePanel.gameLose();
						SendLogoutMessage();
						break; // 종료
					}

					if (Thread.interrupted())
						break;

				} catch (IOException e) {
					try {
						SendLogoutMessage();

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

	public void SendButtonAction(String roomNumber, double x, double y, double xLeftVel, double xRightVel, double yVel,
			boolean upPressed, boolean downPressed, boolean leftPressed, boolean rightPressed, boolean spacePressed) {
		// 좌표와 키 입력 값 보냄
		GameModelMsg objectGameMsg = new GameModelMsg(roomNumber, userName, NetworkStatus.GAME_BUTTON, x, y, xLeftVel,
				xRightVel, yVel, upPressed, downPressed, leftPressed, rightPressed, spacePressed);
		SendObject(objectGameMsg);
	}

	public void SendWinMessage() {
		GameModelMsg obejctGameMsg = new GameModelMsg(userName, NetworkStatus.GAME_WIN);
		SendObject(obejctGameMsg);
	}

	public void SendReadyMessage() {
		GameModelMsg gameReadMsg = new GameModelMsg(userName, NetworkStatus.GAME_READY);
		SendObject(gameReadMsg);
	}

	public void SendLoginMessage(String username) {
		GameModelMsg objectGameMsg = new GameModelMsg(username, NetworkStatus.LOG_IN);
		SendObject(objectGameMsg);
	}

	public void SendLogoutMessage() {
		GameModelMsg objectGameMsg = new GameModelMsg(userName, NetworkStatus.LOG_OUT);
		SendObject(objectGameMsg);
	}
}
