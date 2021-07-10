import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;


public class cop3 extends JFrame {

    private ClientSideConnection csc;

    private JLabel move;
    private JLabel turn;
    private JLabel mrX;
    private JLabel cop1;
    private JLabel cop2;
    private JLabel cop3;
    private JLabel Heading;
    private JLabel Functions;
    private JLabel Powerups;
    private JLabel powerupsLeft;
    private JLabel BlackTicketLeft;
    private JLabel DoubleLeft;
    private JLabel GetCharLeft;
    private JLabel MastermindLeft;
    private JButton FullReverse;
    private JButton AltSwap;
    private JButton LeftShift;
    private JButton RightShift;
    private JButton Double;
    private JButton Mastermind;
    private JButton GetChar;
    private JButton BlackTicket;
    private Container contentPane;
    private JTextField inputField;
    private JButton btnOk;
    private JButton startGame;
    private JLabel CQImage;
    private JTextField ParticipantName;
    private JTextField SchoolName;
    private JLabel participantName;
    private JLabel schoolName;

    private String bMove, bPos, w, SName, PName;
    private final int TURN = 3;
    private int dl = 2;
    private int btl = 2;
    private int gcl = 7;
    private int mml = 7;
    private int m = 0;
    private int MOVE = 0;
    private boolean ButtonEnabled;
    private String mrxPos = "", cop1Pos = "", cop2Pos = "", cop3Pos = "";

    public static void main(String[] args) {
        cop3 kc = new cop3();
        kc.connectToserver();
        kc.startPage();
        //kc.setUpGUI();
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    kc.update();
                }
            }
        });
        t1.start();
    }

    public cop3() {
        contentPane = this.getContentPane();
        FullReverse = new JButton("FullReverse");
        AltSwap = new JButton("AltSwap");
        LeftShift = new JButton("LeftShift");
        RightShift = new JButton("RightShift");
        Heading = new JLabel("CRYPTO-QUEST");
        Functions = new JLabel("FUNCTIONS:");
        Mastermind = new JButton("Mastermind");
        GetChar = new JButton("GetChar");
        Double = new JButton("Double");
        BlackTicket = new JButton("BlackTicket");
        Powerups = new JLabel("POWERUPS:");
        turn = new JLabel("Turn:");
        move = new JLabel("Move:");
        mrX = new JLabel("Mr. X: 123456");
        cop1 = new JLabel("Cop 1: 135246");
        cop2 = new JLabel("Cop 2: 162534");
        cop3 = new JLabel("Cop 3: 543216");
        powerupsLeft = new JLabel("POWERUPS LEFT:");
        BlackTicketLeft = new JLabel("BlackTicket: 2");
        GetCharLeft = new JLabel("GetChar: 7");
        DoubleLeft = new JLabel("Double: 2");
        MastermindLeft = new JLabel("Mastermind: 7");
        inputField = new JTextField("Enter Here");
        btnOk = new JButton("OK");
        startGame = new JButton("Start Game");
        CQImage = new JLabel("");
        ParticipantName = new JTextField();
        SchoolName = new JTextField();
        participantName = new JLabel("Your Name:");
        schoolName = new JLabel("School Name:");
    }

    public void startPage(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);
//        this.setSize(1400,750);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.getContentPane().setBackground(Color.BLACK);
        this.setTitle("CryptoQuest: Cop 3");

        CQImage.setIcon(new ImageIcon("C:\\Users\\sasha\\Desktop\\img1.jpg"));
        CQImage.setBounds(325,10,1900,700);
        contentPane.add(CQImage);

        ParticipantName.setBounds(29, 265, 277, 19);
        ParticipantName.setColumns(10);
        contentPane.add(ParticipantName);

        SchoolName.setBounds(29, 398, 277, 19);
        SchoolName.setColumns(10);
        contentPane.add(SchoolName);

        participantName.setForeground(Color.WHITE);
        participantName.setFont(new Font("Courier New", Font.PLAIN, 25));
        participantName.setBounds(29, 219, 218, 26);
        contentPane.add(participantName);

        schoolName.setForeground(Color.WHITE);
        schoolName.setFont(new Font("Courier New", Font.PLAIN, 25));
        schoolName.setBounds(29, 348, 218, 26);
        contentPane.add(schoolName);

        startGame.setBackground(Color.WHITE);
        startGame.setForeground(Color.BLACK);
        startGame.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        startGame.setBounds(29, 500, 277, 33);
        contentPane.add(startGame);

        ParticipantName.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SchoolName.requestFocusInWindow();
            }
        });

        SchoolName.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ParticipantName.requestFocusInWindow();
            }
        });

        startGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PName = ParticipantName.getText();
                SName = SchoolName.getText();
                csc.sendParticipant(PName, SName);
                if (ParticipantName.getText().equals("") || SchoolName.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Fill in the details first");
                } else {
                    dispose();
                    contentPane.remove(startGame);
                    contentPane.remove(schoolName);
                    contentPane.remove(participantName);
                    contentPane.remove(ParticipantName);
                    contentPane.remove(SchoolName);
                    contentPane.remove(CQImage);
                    setUpGUI();
                    /*Thread t1 = new Thread(new Runnable() {
                        public void run() {
                            while (true) {
                                update();
                            }
                        }
                    });
                    t1.start();*/
                }
            }
        });

        this.setVisible(true);
    }

    public void setUpGUI() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);
        //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.getContentPane().setBackground(Color.WHITE);
        this.setSize(1400,750);
        this.setTitle("CryptoQuest: Cop 3");
        ButtonEnabled = false;
        toggleButtons();
        turn.setText("Turn: Mr. X");

        FullReverse.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        FullReverse.setBounds(25, 554, 141, 45);
        contentPane.add(FullReverse);

        AltSwap.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        AltSwap.setBounds(25, 609, 141, 45);
        contentPane.add(AltSwap);

        LeftShift.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        LeftShift.setBounds(25, 499, 141, 45);
        contentPane.add(LeftShift);

        RightShift.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        RightShift.setBounds(25, 444, 141, 45);
        contentPane.add(RightShift);

        Heading.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 50));
        Heading.setHorizontalAlignment(SwingConstants.CENTER);
        Heading.setBounds(432, 10, 413, 62);
        contentPane.add(Heading);

        Functions.setVerticalAlignment(SwingConstants.BOTTOM);
        Functions.setFont(new Font("Sitka Text", Font.BOLD | Font.ITALIC, 25));
        Functions.setBounds(25, 396, 159, 38);
        contentPane.add(Functions);

        Mastermind.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        Mastermind.setBounds(1155, 609, 141, 45);
        contentPane.add(Mastermind);

        GetChar.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        GetChar.setBounds(1155, 554, 141, 45);
        contentPane.add(GetChar);

        Double.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        Double.setBounds(1155, 499, 141, 45);
        contentPane.add(Double);
        Double.setEnabled(false);

        BlackTicket.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        BlackTicket.setBounds(1155, 444, 141, 45);
        contentPane.add(BlackTicket);
        BlackTicket.setEnabled(false);

        Powerups.setVerticalAlignment(SwingConstants.BOTTOM);
        Powerups.setFont(new Font("Sitka Text", Font.BOLD | Font.ITALIC, 25));
        Powerups.setBounds(1145, 396, 159, 38);
        contentPane.add(Powerups);

        turn.setFont(new Font("Lucida Sans", Font.PLAIN, 25));
        turn.setBounds(25, 146, 157, 41);
        contentPane.add(turn);

        move.setFont(new Font("Lucida Sans", Font.PLAIN, 25));
        move.setBounds(25, 253, 220, 41);
        contentPane.add(move);

        mrX.setFont(new Font("Lucida Sans", Font.PLAIN, 20));
        mrX.setBounds(227, 679, 141, 24);
        contentPane.add(mrX);

        cop1.setFont(new Font("Lucida Sans", Font.PLAIN, 20));
        cop1.setBounds(468, 679, 166, 24);
        contentPane.add(cop1);

        cop2.setFont(new Font("Lucida Sans", Font.PLAIN, 20));
        cop2.setBounds(717, 679, 166, 24);
        contentPane.add(cop2);

        cop3.setFont(new Font("Lucida Sans", Font.PLAIN, 20));
        cop3.setBounds(945, 679, 166, 24);
        contentPane.add(cop3);

        powerupsLeft.setFont(new Font("Sitka Text", Font.BOLD | Font.ITALIC, 25));
        powerupsLeft.setBounds(1145, 150, 231, 38);
        contentPane.add(powerupsLeft);

        BlackTicketLeft.setFont(new Font("Lucida Sans", Font.PLAIN, 20));
        BlackTicketLeft.setBounds(1155, 182, 200, 24);
        contentPane.add(BlackTicketLeft);

        GetCharLeft.setFont(new Font("Lucida Sans", Font.PLAIN, 20));
        GetCharLeft.setBounds(1155, 236, 200, 24);
        contentPane.add(GetCharLeft);

        DoubleLeft.setFont(new Font("Lucida Sans", Font.PLAIN, 20));
        DoubleLeft.setBounds(1155, 209, 200, 24);
        contentPane.add(DoubleLeft);

        MastermindLeft.setFont(new Font("Lucida Sans", Font.PLAIN, 20));
        MastermindLeft.setBounds(1155, 263, 200, 24);
        contentPane.add(MastermindLeft);

        inputField.setBounds(1164, 331, 126, 30);
        inputField.setFont(new Font("Consolas", Font.PLAIN, 20));
        inputField.setColumns(10);
        inputField.setEditable(false);
        contentPane.add(inputField);

        btnOk.setBounds(1301, 337, 57, 21);
        btnOk.setEnabled(false);
        contentPane.add(btnOk);

        functions f = new functions();
        RightShift.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cop3.setText("Cop 3: " + f.RightShift(cop3.getText().substring(7)));
                move.setText("Move: Cyclic Shift");
                bMove = RightShift.getText();
                bPos = cop3.getText().substring(7);
                csc.sendMoveAndPos(bMove, bPos, TURN);
            }
        });

        LeftShift.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cop3.setText("Cop 3: " + f.LeftShift(cop3.getText().substring(7)));
                move.setText("Move: Cyclic Shift");
                bMove = LeftShift.getText();
                bPos = cop3.getText().substring(7);
                csc.sendMoveAndPos(bMove, bPos, TURN);
            }
        });

        AltSwap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cop3.setText("Cop 3: " + f.AltSwap(cop3.getText().substring(7)));
                move.setText("Move: Swap");
                bMove = AltSwap.getText();
                bPos = cop3.getText().substring(7);
                csc.sendMoveAndPos(bMove, bPos, TURN);
            }
        });

        FullReverse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cop3.setText("Cop 3: " + f.FullReverse(cop3.getText().substring(7)));
                move.setText("Move: Swap");
                bMove = FullReverse.getText();
                bPos = cop3.getText().substring(7);
                csc.sendMoveAndPos(bMove, bPos, TURN);
            }
        });

        GetChar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (gcl <= 0) {
                    JOptionPane.showMessageDialog(null, "Sorry, no more GetChar Powerup left");
                } else {
                    inputField.setEditable(true);
                    btnOk.setEnabled(true);
                    inputField.setText("");
                    Mastermind.setEnabled(false);
                }
            }
        });

        Mastermind.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (mml <= 0) {
                    JOptionPane.showMessageDialog(null, "Sorry, no more Mastermind Powerup left");
                } else {
                    inputField.setEditable(true);
                    btnOk.setEnabled(true);
                    inputField.setText("");
                    GetChar.setEnabled(false);
                }
            }
        });


        btnOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String k = inputField.getText();
                if (k.length() == 1 && !Mastermind.isEnabled()) {
                    if (Integer.parseInt(k) > 0 && Integer.parseInt(k) <= 6) {
                        String m = w;
                        JOptionPane.showMessageDialog(null, "The character at position " + k + " is " + m.charAt(Integer.parseInt(k) - 1));
                        inputField.setEditable(false);
                        btnOk.setEnabled(false);
                        GetChar.setEnabled(false);
                        bMove = GetChar.getText();
                        csc.sendMoveAndPos(bMove, "none", TURN);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Input. Please try again");
                        inputField.setText("");
                    }
                } else if (k.length() == 6 && !GetChar.isEnabled()) {
                    if (k.contains("1") && k.contains("2") && k.contains("3") && k.contains("4") && k.contains("5") && k.contains("6")) {
                        String m = w;
                        int c = 0;
                        for (int i = 0; i < 6; i++) {
                            if (k.charAt(i) == m.charAt(i)) {
                                c++;
                            }
                        }
                        JOptionPane.showMessageDialog(null, c + "  characters match with Mr. X's position");
                        inputField.setEditable(false);
                        btnOk.setEnabled(false);
                        Mastermind.setEnabled(false);
                        bMove = Mastermind.getText();
                        csc.sendMoveAndPos(bMove, "none", TURN);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Input. Please try again");
                        inputField.setText("");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Input. Please try again");
                    inputField.setText("");
                }

            }
        });

        Map();
        this.setVisible(true);
    }

    public void Map(){
        JLabel label = new JLabel("345612");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label.setBounds(562, 82, 43, 15);
        getContentPane().add(label);

        JLabel label_1 = new JLabel("216543");
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        label_1.setForeground(Color.BLACK);
        label_1.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_1.setBounds(701, 82, 43, 15);
        getContentPane().add(label_1);

        JLabel label_2 = new JLabel("165432");
        label_2.setHorizontalAlignment(SwingConstants.CENTER);
        label_2.setForeground(Color.BLACK);
        label_2.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_2.setBounds(474, 127, 43, 15);
        getContentPane().add(label_2);

        JLabel label_3 = new JLabel("456123");
        label_3.setHorizontalAlignment(SwingConstants.CENTER);
        label_3.setForeground(Color.BLACK);
        label_3.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_3.setBounds(792, 127, 43, 15);
        getContentPane().add(label_3);

        JLabel label_4 = new JLabel("234561");
        label_4.setHorizontalAlignment(SwingConstants.CENTER);
        label_4.setForeground(Color.BLACK);
        label_4.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_4.setBounds(360, 156, 43, 15);
        getContentPane().add(label_4);

        JLabel label_5 = new JLabel("321654");
        label_5.setHorizontalAlignment(SwingConstants.CENTER);
        label_5.setForeground(Color.BLACK);
        label_5.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_5.setBounds(895, 156, 43, 15);
        getContentPane().add(label_5);

        JLabel label_6 = new JLabel("325416");
        label_6.setHorizontalAlignment(SwingConstants.CENTER);
        label_6.setForeground(Color.BLACK);
        label_6.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_6.setBounds(319, 243, 43, 15);
        getContentPane().add(label_6);

        JLabel label_7 = new JLabel("614523");
        label_7.setHorizontalAlignment(SwingConstants.CENTER);
        label_7.setForeground(Color.BLACK);
        label_7.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_7.setBounds(432, 209, 43, 15);
        getContentPane().add(label_7);

        JLabel label_8 = new JLabel("436521");
        label_8.setHorizontalAlignment(SwingConstants.CENTER);
        label_8.setForeground(Color.BLACK);
        label_8.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_8.setBounds(562, 169, 43, 15);
        getContentPane().add(label_8);

        JLabel label_9 = new JLabel("125634");
        label_9.setHorizontalAlignment(SwingConstants.CENTER);
        label_9.setForeground(Color.BLACK);
        label_9.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_9.setBounds(701, 169, 43, 15);
        getContentPane().add(label_9);

        JLabel label_10 = new JLabel("365214");
        label_10.setHorizontalAlignment(SwingConstants.CENTER);
        label_10.setForeground(Color.BLACK);
        label_10.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_10.setBounds(555, 255, 43, 15);
        getContentPane().add(label_10);

        JLabel label_11 = new JLabel("412563");
        label_11.setHorizontalAlignment(SwingConstants.CENTER);
        label_11.setForeground(Color.BLACK);
        label_11.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_11.setBounds(723, 255, 43, 15);
        getContentPane().add(label_11);

        JLabel label_12 = new JLabel("561234");
        label_12.setHorizontalAlignment(SwingConstants.CENTER);
        label_12.setForeground(Color.BLACK);
        label_12.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_12.setBounds(816, 209, 43, 15);
        getContentPane().add(label_12);

        JLabel label_13 = new JLabel("432165");
        label_13.setHorizontalAlignment(SwingConstants.CENTER);
        label_13.setForeground(Color.BLACK);
        label_13.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_13.setBounds(939, 243, 43, 15);
        getContentPane().add(label_13);

        JLabel label_14 = new JLabel("632541");
        label_14.setHorizontalAlignment(SwingConstants.CENTER);
        label_14.setForeground(Color.BLACK);
        label_14.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_14.setBounds(301, 346, 43, 15);
        getContentPane().add(label_14);

        JLabel label_15 = new JLabel("145236");
        label_15.setHorizontalAlignment(SwingConstants.CENTER);
        label_15.setForeground(Color.BLACK);
        label_15.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_15.setBounds(381, 319, 43, 15);
        getContentPane().add(label_15);

        JLabel label_16 = new JLabel("652143");
        label_16.setHorizontalAlignment(SwingConstants.CENTER);
        label_16.setForeground(Color.BLACK);
        label_16.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_16.setBounds(895, 319, 43, 15);
        getContentPane().add(label_16);

        JLabel label_17 = new JLabel("341256");
        label_17.setHorizontalAlignment(SwingConstants.CENTER);
        label_17.setForeground(Color.BLACK);
        label_17.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_17.setBounds(980, 346, 43, 15);
        getContentPane().add(label_17);

        JLabel label_18 = new JLabel("541632");
        label_18.setHorizontalAlignment(SwingConstants.CENTER);
        label_18.setForeground(Color.BLACK);
        label_18.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_18.setBounds(772, 376, 43, 15);
        getContentPane().add(label_18);

        JLabel label_19 = new JLabel("654321");
        label_19.setHorizontalAlignment(SwingConstants.CENTER);
        label_19.setForeground(Color.BLACK);
        label_19.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_19.setBounds(519, 376, 43, 15);
        getContentPane().add(label_19);

        JLabel label_20 = new JLabel("543216");
        label_20.setHorizontalAlignment(SwingConstants.CENTER);
        label_20.setForeground(Color.BLACK);
        label_20.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_20.setBounds(597, 426, 43, 15);
        getContentPane().add(label_20);

        JLabel label_21 = new JLabel("254163");
        label_21.setHorizontalAlignment(SwingConstants.CENTER);
        label_21.setForeground(Color.BLACK);
        label_21.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_21.setBounds(701, 426, 43, 15);
        getContentPane().add(label_21);

        JLabel label_22 = new JLabel("612345");
        label_22.setHorizontalAlignment(SwingConstants.CENTER);
        label_22.setForeground(Color.BLACK);
        label_22.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_22.setBounds(474, 474, 43, 15);
        getContentPane().add(label_22);

        JLabel label_23 = new JLabel("361452");
        label_23.setHorizontalAlignment(SwingConstants.CENTER);
        label_23.setForeground(Color.BLACK);
        label_23.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_23.setBounds(792, 474, 43, 15);
        getContentPane().add(label_23);

        JLabel label_24 = new JLabel("123456");
        label_24.setHorizontalAlignment(SwingConstants.CENTER);
        label_24.setForeground(Color.BLACK);
        label_24.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_24.setBounds(360, 426, 43, 15);
        getContentPane().add(label_24);

        JLabel label_25 = new JLabel("236145");
        label_25.setHorizontalAlignment(SwingConstants.CENTER);
        label_25.setForeground(Color.BLACK);
        label_25.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_25.setBounds(924, 426, 43, 15);
        getContentPane().add(label_25);

        JLabel label_26 = new JLabel("163254");
        label_26.setHorizontalAlignment(SwingConstants.CENTER);
        label_26.setForeground(Color.BLACK);
        label_26.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_26.setBounds(319, 516, 43, 15);
        getContentPane().add(label_26);

        JLabel label_27 = new JLabel("452361");
        label_27.setHorizontalAlignment(SwingConstants.CENTER);
        label_27.setForeground(Color.BLACK);
        label_27.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_27.setBounds(381, 554, 43, 15);
        getContentPane().add(label_27);

        JLabel label_28 = new JLabel("416325");
        label_28.setHorizontalAlignment(SwingConstants.CENTER);
        label_28.setForeground(Color.BLACK);
        label_28.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_28.setBounds(462, 609, 43, 15);
        getContentPane().add(label_28);

        JLabel label_29 = new JLabel("214365");
        label_29.setHorizontalAlignment(SwingConstants.CENTER);
        label_29.setForeground(Color.BLACK);
        label_29.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_29.setBounds(597, 639, 43, 15);
        getContentPane().add(label_29);

        JLabel label_30 = new JLabel("523614");
        label_30.setHorizontalAlignment(SwingConstants.CENTER);
        label_30.setForeground(Color.BLACK);
        label_30.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_30.setBounds(701, 639, 43, 15);
        getContentPane().add(label_30);

        JLabel label_31 = new JLabel("563412");
        label_31.setHorizontalAlignment(SwingConstants.CENTER);
        label_31.setForeground(Color.BLACK);
        label_31.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_31.setBounds(826, 609, 43, 15);
        getContentPane().add(label_31);

        JLabel label_32 = new JLabel("521436");
        label_32.setHorizontalAlignment(SwingConstants.CENTER);
        label_32.setForeground(Color.BLACK);
        label_32.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_32.setBounds(895, 554, 43, 15);
        getContentPane().add(label_32);

        JLabel label_33 = new JLabel("634125");
        label_33.setHorizontalAlignment(SwingConstants.CENTER);
        label_33.setForeground(Color.BLACK);
        label_33.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_33.setBounds(939, 516, 43, 15);
        getContentPane().add(label_33);

        JLabel label_34 = new JLabel("143652");
        label_34.setHorizontalAlignment(SwingConstants.CENTER);
        label_34.setForeground(Color.BLACK);
        label_34.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_34.setBounds(555, 554, 43, 15);
        getContentPane().add(label_34);

        JLabel label_35 = new JLabel("256341");
        label_35.setHorizontalAlignment(SwingConstants.CENTER);
        label_35.setForeground(Color.BLACK);
        label_35.setFont(new Font("Lucida Bright", Font.BOLD, 12));
        label_35.setBounds(758, 554, 43, 15);
        getContentPane().add(label_35);
    }

    public void toggleButtons() {
        RightShift.setEnabled(ButtonEnabled);
        LeftShift.setEnabled(ButtonEnabled);
        AltSwap.setEnabled(ButtonEnabled);
        FullReverse.setEnabled(ButtonEnabled);
        GetChar.setEnabled(ButtonEnabled);
        Mastermind.setEnabled(ButtonEnabled);
    }

    public void updatePowerupsLeft(String s) {
        if (s.equals("GetChar")) {
            gcl -= 1;
            GetCharLeft.setText("GetChar: " + gcl);
        } else if (s.equals("Mastermind")) {
            mml -= 1;
            MastermindLeft.setText("Mastermind: " + mml);
        } else if (s.equals("Double")) {
            dl -= 1;
            DoubleLeft.setText("Double: " + dl);
        } else if (s.equals("BlackTicket")) {
            btl -= 1;
            BlackTicketLeft.setText("BlackTicket: " + btl);
        }
    }

    public void update() {
        String s[] = csc.receiveMoveAndPos();
        if (s[1].equals("none")) {
            updatePowerupsLeft(s[0]);
        } else {
            if (Integer.parseInt(s[3]) != 100) {
                if (s[0].equals("RightShift") || s[0].equals("LeftShift")) {
                    move.setText("Move: Cyclic Shift");
                } else if (s[0].equals("AltSwap") || s[0].equals("FullReverse")) {
                    move.setText("Move: Swap");
                }
                updatePos(s[1], Integer.parseInt(s[2]), Integer.parseInt(s[3]));
            } else {
                move.setText("Move: ");
                updatePos(s[1], Integer.parseInt(s[2]), Integer.parseInt(s[3]));
            }
        }
    }

    public void updatePos(String s, int n, int mrxp) {
        if (n == 0) {
            MOVE++;
            w = s;
            mrxPos = s;
            if (mrxp==1) {
                if (m % 4 == 0) {
                    mrX.setText("Mr. X: " + mrxPos);
                }
                checkWinner(mrxPos, cop1Pos, cop2Pos, cop3Pos);
                ButtonEnabled = false;
                toggleButtons();
                turn.setText("Turn: Mr. X");
            } else if (mrxp == 100) {
                m++;
                checkWinner(mrxPos, cop1Pos, cop2Pos, cop3Pos);
                ButtonEnabled = false;
                toggleButtons();
                turn.setText("Turn: Cop 1");
            }  else {
                m++;
                if (m % 4 == 0) {
                    mrX.setText("Mr. X: " + mrxPos);
                }
                checkWinner(mrxPos, cop1Pos, cop2Pos, cop3Pos);
                ButtonEnabled = false;
                toggleButtons();
                turn.setText("Turn: Cop 1");
            }
        } else if (n == 1) {
            cop1Pos = s;
            cop1.setText("Cop 1: " + cop1Pos);
            checkWinner(mrxPos, cop1Pos, cop2Pos, cop3Pos);
            ButtonEnabled = false;
            toggleButtons();
            turn.setText("Turn: Cop 2");
        } else if (n == 2) {
            cop2Pos = s;
            cop2.setText("Cop 2: " + cop2Pos);
            checkWinner(mrxPos, cop1Pos, cop2Pos, cop3Pos);
            ButtonEnabled = true;
            toggleButtons();
            turn.setText("Turn: Cop 3");
        } else if (n == 3) {
            cop3Pos = s;
            cop3.setText("Cop 3: " + cop3Pos);
            checkWinner(mrxPos, cop1Pos, cop2Pos, cop3Pos);
            ButtonEnabled = false;
            toggleButtons();
            turn.setText("Turn: Mr. X");
        }
    }

    public void checkWinner(String s1, String s2, String s3, String s4) {
        ButtonEnabled = false;
        if(s1.equals(s2) || s1.equals(s3) || s1.equals(s4)) {
            JOptionPane.showMessageDialog(null, "Congratulations!! You caught the wicked Mr. X \nYou win.");
            csc.closeConnection();
        } else if (MOVE == 20) {
            JOptionPane.showMessageDialog(null, "Sorry!! Mr. X has successfully escaped from your clutches.\n You Lose.");
            csc.closeConnection();
        }
    }

    public void connectToserver() {
        csc = new ClientSideConnection();
    }

    private class ClientSideConnection {
        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;

        public ClientSideConnection() {
            System.out.println("-----Client-----");
            try {
                socket = new Socket("localhost", 10101);
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
                int playerID = dataIn.readInt();
                System.out.println("Cop 3 Connected to server as client # " + playerID + ".");
            } catch (IOException ex) {
                System.out.println("IOException from CSC Constructor");
            }
        }

        public void sendMoveAndPos(String s1, String s2, int n) {
            try {
                dataOut.writeInt(n);
                dataOut.flush();
                dataOut.writeUTF(s1);
                dataOut.flush();
                dataOut.writeUTF(s2);
                dataOut.flush();
            } catch (IOException ex) {
                System.out.println("IOException from sendMoveAndPos() CSC");
            }
        }

        public String[] receiveMoveAndPos() {
            String s1, s2;
            String[] sArr = new String[4];
            int n, MRXP;
            try {
                n = dataIn.readInt();
                s1 = dataIn.readUTF();
                s2 = dataIn.readUTF();
                MRXP = dataIn.readInt();
                sArr[0] = s1;
                sArr[1] = s2;
                sArr[2] = Integer.toString(n);
                sArr[3] = Integer.toString(MRXP);
            } catch (IOException ex) {
                System.out.println("IOException from receiveMoveAndPos() CSC");
            }
            return sArr;
        }

        public void sendParticipant(String pn, String sn){
            try{
                dataOut.writeUTF(pn);
                dataOut.flush();
                dataOut.writeUTF(sn);
                dataOut.flush();
                dataOut.writeInt(3);
                dataOut.flush();
            } catch(IOException ex){
                System.out.println("IOException from sendParticipant CSC");
            }
        }

        public void closeConnection(){
            try{
                socket.close();
                System.out.println("---CONNECTION CLOSED---");
                dispose();
            } catch(IOException ex) {
                System.out.println("IOException from closeConnection() CSC");
            }
        }
    }
}


