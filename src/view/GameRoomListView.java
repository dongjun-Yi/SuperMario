package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import client.GameClient;
import main.GamePanel;

public class GameRoomListView extends JFrame implements GameStatusView {
	private static final long serialVersionUID = 3L;

	private ImageLoader imageLoader = ImageLoader.getImageLoader();

	private GamePanel gamePanel;
	private GameClient gameClient;
	private String[] userList;
	private JButton makeRoomBtn;
	private JLabel label;
	private JButton[] btnNewButton;
	private JLabel[] userCntLabel;
	JScrollPane scroll;
	JPanel panel = new JPanel();
	private int i;

	private Font font = FontLoader.getInstance().loadMarioFont();

	public GameRoomListView(GamePanel gamePanel, GameClient gameClient, String roomList[]) {
		this.gamePanel = gamePanel;
		this.gameClient = gameClient;
		this.setLayout(new BorderLayout());

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				gamePanel.gameStartScreen();
			}
		});
		panel.setBackground(new Color(0, 252, 255));
		panel.setLayout(new GridLayout(15, 2));

		drawGameRoomView(roomList);
		scroll = new JScrollPane(panel);

		getContentPane().add(scroll);
		this.setSize(500, 400);
		this.setLocationRelativeTo(null); // 화면 중앙
		this.setVisible(true);
		this.setResizable(false);
	}

	public void drawGameRoomView(String roomList[]) {

		// makeRoomBtn = new JButton("방 생성하기");
		makeRoomBtn = new JButton("CREATE ROOM");
		makeRoomBtn.setFont(font.deriveFont(10f));
		makeRoomBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameClient.SendMakeRoomRequestMessage();
			}
		});
		// makeRoomBtn.setBounds(180, 300, 142, 38);
		// makeRoomBtn.setPreferredSize(new Dimension(142, 38));
		getContentPane().add(makeRoomBtn, BorderLayout.SOUTH);

		if (roomList == null) {
			// label = new JLabel("아직 방이 없습니다.");
			label = new JLabel("NO GAME ROOM");
			label.setFont(font.deriveFont(20f));
			label.setBounds(155, 100, 542, 38);
			// label.setPreferredSize(new Dimension(50, 40));
			panel.add(label);
		} else {
			userList = roomList;

			btnNewButton = new JButton[userList.length];
			userCntLabel = new JLabel[userList.length];

			for (i = 0; i < userList.length; i++) {
				String roomInfo[];
				roomInfo = userList[i].split(" ");

				// 방 참가 버튼
				// btnNewButton[i] = new JButton("방" + (i + 1));
				btnNewButton[i] = new JButton("ROOM" + (i + 1));
				btnNewButton[i].setFont(font.deriveFont(15f));
				btnNewButton[i].setBounds(100, 52 + (i * 60), 200, 60);
				//btnNewButton[i].setPreferredSize(new Dimension(50, 40));
				panel.add(btnNewButton[i]);

				// 방 유저 수
				userCntLabel[i] = new JLabel(roomInfo[1] + "/2");
				userCntLabel[i].setBounds(410, 52 + (i * 60), 200, 60);
				//userCntLabel[i].setPreferredSize(new Dimension(50, 20));
				panel.add(userCntLabel[i]);

				btnNewButton[i].addActionListener(new ActionListener() {
					String roomNumber = roomInfo[0];
					String roomUsr = roomInfo[1];

					@Override
					public void actionPerformed(ActionEvent e) {
						// 인원 수 꽉 차지 않으면 참가
						if (!roomUsr.equals("2")) {
							dispose();
							gameClient.SendReadyMessage(roomNumber);
							boolean isPlayer1 = (roomUsr.equals("0")) ? true : false; // 인원 수 0명이면 1p:마리오가 됨, 이미 한명이 있으면
																						// 2p:루이지가 됨
							gamePanel.gameReady(isPlayer1);
						} else
							errorMsg();
					}
				});
			}
		}
	}

	// 새로고침을 위해 컴포넌트 제거
	public void removeList() {
		if (makeRoomBtn != null) {
			panel.remove(makeRoomBtn);
			makeRoomBtn = null;
		}
		if (label != null) {
			panel.remove(label);
			label = null;
		}
		if (userList != null) {
			for (i = 0; i < userList.length; i++) {
				if (btnNewButton[i] != null)
					panel.remove(btnNewButton[i]);
				if (userCntLabel[i] != null)
					panel.remove(userCntLabel[i]);
			}
			btnNewButton = null;
			userCntLabel = null;
			userList = null;
		}
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

	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(imageLoader.getStartScreenDisabledImage(), 0, 0, null);
	}
}
