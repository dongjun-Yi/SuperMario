package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import client.GameClient;
import main.GamePanel;
import server.GameModelMsg;
import server.NetworkStatus;

public class GameRoomMakeView extends JFrame implements GameStatusView {
	GameClient gameClient;
	private String[] userList;
	JLabel label;
	int i;

	public GameRoomMakeView(GameClient gameClient, String roomList[]) {
		this.gameClient = gameClient;
		getContentPane().setBackground(new Color(0, 252, 255));
		getContentPane().setLayout(null);
		long roomNumber = System.nanoTime();

		JButton makeRoomBtn = new JButton("방 생성하기");
		makeRoomBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameModelMsg gameReadMsg = new GameModelMsg(String.valueOf(roomNumber), "player",
						NetworkStatus.MAKE_ROOM_REQUEST);
				gameClient.SendObject(gameReadMsg);
				dispose();
			}
		});
		makeRoomBtn.setBounds(180, 300, 142, 38);
		getContentPane().add(makeRoomBtn);

		if (roomList == null) {
			label = new JLabel("아직 방이 없습니다.");
			label.setBounds(200, 100, 142, 38);
			getContentPane().add(label);
		} else {
			userList = roomList;
			for (i = 0; i < userList.length; i++) {
				JButton[] btnNewButton = new JButton[userList.length];
				btnNewButton[i] = new JButton("방" + (i + 1));
				btnNewButton[i].setBounds(100, 52 + (i * 60), 300, 60);
				getContentPane().add(btnNewButton[i]);

				btnNewButton[i].addActionListener(new ActionListener() {
					int j = i;

					@Override
					public void actionPerformed(ActionEvent e) {
						GameModelMsg gameStartMsg = new GameModelMsg(userList[j], "player", NetworkStatus.GAME_READY);
						gameClient.SendObject(gameStartMsg);
						dispose();
					}
				});
			}
		}

		this.setSize(500, 400);
		this.setVisible(true);
	}

	@Override
	public void updates() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub

	}

}
