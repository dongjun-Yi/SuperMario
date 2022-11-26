package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import client.GameClient;

public class GameRoomListView extends JFrame implements GameStatusView {
	private static final long serialVersionUID = 3L;
	
	private GameClient gameClient;
	private String[] userList;
	private JButton makeRoomBtn;
	private JLabel label;
	private JButton[] btnNewButton;
	private int i;

	public GameRoomListView(GameClient gameClient, String roomList[]) {
		this.gameClient = gameClient;
		
		getContentPane().setBackground(new Color(0, 252, 255));
		getContentPane().setLayout(null);
			
		drawGameRoomView(roomList);
		
		this.setSize(500, 400);
		this.setVisible(true);
	}

	public void drawGameRoomView(String roomList[]) {
	
		makeRoomBtn = new JButton("방 생성하기");
		makeRoomBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameClient.SendMakeRoomRequestMessage();
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
				btnNewButton = new JButton[userList.length];
				btnNewButton[i] = new JButton("방" + (i + 1));
				btnNewButton[i].setBounds(100, 52 + (i * 60), 300, 60);
				getContentPane().add(btnNewButton[i]);

				btnNewButton[i].addActionListener(new ActionListener() {
					int j = i;
					@Override
					public void actionPerformed(ActionEvent e) {
						gameClient.SendReadyMessage(userList[j]);
					}
				});
			}
		}
	}

	public void removeList() {
		if(makeRoomBtn != null) {
			remove(makeRoomBtn);
			makeRoomBtn = null;
		}
		if(label != null) {
			remove(label);
			label = null;
		}
		if(userList != null) {
			for (i = 0; i < userList.length; i++) {
				if(btnNewButton[i] != null)
					remove(btnNewButton[i]);
			}
			btnNewButton = null;
			userList = null;
		}
	}
	
	public void disposeView() {
		dispose();
	}
	
	public void errorMsg() {
		JOptionPane.showMessageDialog(this, "유저 수가 이미 꽉 차있어서 입장 불가", "경고", JOptionPane.WARNING_MESSAGE);
	}

	public void updateRoomListView(String roomList[]) {
		removeList();
		drawGameRoomView(roomList);
		repaint();
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
