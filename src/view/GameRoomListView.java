package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import client.GameClient;
import main.GamePanel;
import java.awt.BorderLayout;
import javax.swing.ScrollPaneConstants;

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
	private JLabel[] roomNumLabel;
	private JLabel[] roomStateLabel;
	private JLabel roomlistLabel;
	
	private JScrollPane scrollPane;
	private JPanel[] panels; // 방리스트 항목들
	private JPanel TopPanel;
	private JPanel bottomPanel; // 방 생성 버튼 패널
	private JPanel container; // 방리스트 패널

	private int scrollMaxHeight = 235; // ScrollPane의 최대 크기

	private Font font = FontLoader.getInstance().loadMarioFont();

	public GameRoomListView(GamePanel gamePanel, GameClient gameClient, String roomList[]) {
		setTitle("Room List");
		
		this.gamePanel = gamePanel;
		this.gameClient = gameClient;

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				gamePanel.gameStartScreen();
			}
		});

		makeRoomBtn = new JButton("CREATE ROOM");
		makeRoomBtn.setPreferredSize(new Dimension(148, 41));
		makeRoomBtn.setFont(font.deriveFont(10f));
		makeRoomBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameClient.SendMakeRoomRequestMessage();
			}
		});

		this.setSize(500, 390);
		this.setLocationRelativeTo(null); // 화면 중앙 위치
		this.setVisible(true);
		this.setResizable(false);

		TopPanel = new JPanel();
		TopPanel.setLayout(null);
		TopPanel.setPreferredSize(new Dimension(500, 60));
		TopPanel.setBackground(new Color(92, 148, 252));
		
		roomlistLabel = new JLabel("ROOM LIST");
		roomlistLabel.setFont(font.deriveFont(20f));
		roomlistLabel.setBounds(12, 27, 148, 23);
		
		TopPanel.add(roomlistLabel);
		getContentPane().add(TopPanel, BorderLayout.NORTH);
		
		bottomPanel = new JPanel();
		bottomPanel.add(makeRoomBtn);
		getContentPane().add(bottomPanel, BorderLayout.SOUTH);

		scrollPane = new JScrollPane();
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);	
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		container = new JPanel();
		container.setBackground(new Color(92, 148, 252));
		container.setLayout(null);
		container.setPreferredSize(new Dimension(500, scrollMaxHeight));
		
		drawGameRoomView(roomList);
		scrollPane.setViewportView(container);
		scrollPane.setPreferredSize(new Dimension(500, scrollMaxHeight));
	}

	public void drawGameRoomView(String roomList[]) {

		if (roomList == null) {
			label = new JLabel("NO GAME ROOM");
			label.setFont(font.deriveFont(20f));
			label.setBounds(145, 99, 195, 38);
			container.add(label);
		} else {
			
			int panelWidth = 488;
			int panelHeight = 80;

			userList = roomList;

			panels = new JPanel[userList.length];
			btnNewButton = new JButton[userList.length];
			userCntLabel = new JLabel[userList.length];
			roomNumLabel = new JLabel[userList.length];
			roomStateLabel =  new JLabel[userList.length];

			for (int i = 0; i < userList.length; i++) {

				String roomInfo[];
				roomInfo = userList[i].split(" ");
				
				// 방 리스트 항목 패널
				panels[i] = new JPanel();
				panels[i].setBounds(0, i * 80, panelWidth, panelHeight);
				panels[i].setLayout(null);
				if (i % 2 == 0)
					panels[i].setBackground(new Color(194, 194, 194));	// 짝수번 배경색 입힘
			
				// 방 참가 버튼
				btnNewButton[i] = new JButton("Join");
				btnNewButton[i].setFont(font.deriveFont(15f));
				btnNewButton[i].setBounds(373, 9, 90, 60);
				panels[i].add(btnNewButton[i]);

				// 방 유저 수
				userCntLabel[i] = new JLabel(roomInfo[1] + "/2");
				userCntLabel[i].setFont(new Font("Arial Black", Font.PLAIN, 12));
				userCntLabel[i].setBounds(335, 15, 26, 49);
				panels[i].add(userCntLabel[i]);

				// 방 이름
				roomNumLabel[i] = new JLabel("ROOM " + (i + 1));
				roomNumLabel[i].setFont(font.deriveFont(15f));
				roomNumLabel[i].setBounds(26, 9, 73, 60);
				panels[i].add(roomNumLabel[i]);
				
				// 방 상태
				roomStateLabel[i] = new JLabel("In GAME");
				if (!roomInfo[1].equals("2")) roomStateLabel[i].setText("  LOBBY");
				roomStateLabel[i].setFont(font.deriveFont(10f));
				roomStateLabel[i].setBounds(270, 18, 54, 49);
				panels[i].add(roomStateLabel[i]);
				
				// 패널에 방 항목 추가
				container.add(panels[i]);

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

			// (ScrollPane 크기 < 내부 Panel 크기 -> 스크롤바 나타남)
			// 추가된 패널 수의 높이가 scrollPane 높이보다 높아지면
			// container의 높이를 증가시켜서 스크롤바가 나타나게 함
			if (panelHeight * userList.length > scrollMaxHeight)
				container.setPreferredSize(new Dimension(500, panelHeight * userList.length));
		}
	}

	// 새로고침을 위해 컴포넌트 제거
	public void removeList() {
		if (label != null) {
			container.remove(label);
			label = null;
		}
		
		if (userList != null) {
			for (int i = 0; i < userList.length; i++) {
				if (btnNewButton[i] != null)
					panels[i].remove(btnNewButton[i]);
				if (userCntLabel[i] != null)
					panels[i].remove(userCntLabel[i]);
				if (roomNumLabel[i] != null)
					panels[i].remove(roomNumLabel[i]);
				if (roomStateLabel[i] != null)
					panels[i].remove(roomStateLabel[i]);
				container.remove(panels[i]);
				panels[i] = null;
			}
			panels = null;
			btnNewButton = null;
			userCntLabel = null;
			roomNumLabel = null;
			roomStateLabel = null;
			userList = null;
		}
	}

	public void errorMsg() {
		JOptionPane.showMessageDialog(this, "유저 수가 이미 꽉 차있어서 입장 불가", "경고", JOptionPane.WARNING_MESSAGE);
	}

	public void updateRoomListView(String roomList[]) {
		removeList();
		drawGameRoomView(roomList);
		validate();
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
