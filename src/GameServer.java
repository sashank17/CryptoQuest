import java.io.*;
import java.net.*;

public class GameServer {
    private ServerSocket ss;
    private int numPlayers;
    private int m = 0;
    private ServerSideConnection client1;
    private ServerSideConnection client2;
    private ServerSideConnection client3;
    private ServerSideConnection client4;

    private String mrXPos = "123456";
    private String mrXsn;
    private String mrXpn;
    private String cop1Pos = "135246";
    private String cop1sn;
    private String cop1pn;
    private String cop2Pos = "162534";
    private String cop2sn;
    private String cop2pn;
    private String cop3Pos = "543216";
    private String cop3sn;
    private String cop3pn;

    public GameServer() {
        System.out.println("-----Game Server-----");
        numPlayers = 0;
        try {
            ss = new ServerSocket(10101);
        } catch (IOException ex) {
            System.out.println("IOException from Game Server Constructor");
        }
    }

    public void acceptConnections() {
        try {
            System.out.println("Waiting for Connections......");
            while (numPlayers < 4) {
                Socket s = ss.accept();
                numPlayers++;
                System.out.println("Client #" + numPlayers + " has connected.");
                ServerSideConnection ssc = new ServerSideConnection(s, numPlayers);
                if (numPlayers == 1) {
                    client1 = ssc;
                } else if (numPlayers == 2) {
                    client2 = ssc;
                } else if (numPlayers == 3) {
                    client3 = ssc;
                } else {
                    client4 = ssc;
                }
                Thread t = new Thread(ssc);
                t.start();
            }
            System.out.println("No longer Accepting connections");
        } catch (IOException ex) {
            System.out.println("IOException from acceptConnections()");
        }
    }

    private class ServerSideConnection implements Runnable {
        private final Socket socket;
        private final int playerID;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;

        public ServerSideConnection(Socket s, int id) {
            socket = s;
            playerID = id;
            try {
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
            } catch (IOException ex) {
                System.out.println("IOException from SSC Constructor");
            }
        }

        public void run() {
            try {
                dataOut.writeInt(playerID);
                dataOut.flush();
                receiveParticipant();
                while (true) {
                    receiveMoveAndPos();
                    if(checkWinner()) {
                        System.out.println("GAME OVER");
                        break;
                    }
                }
                client1.closeConnection();
                client2.closeConnection();
                client3.closeConnection();
                client4.closeConnection();
                System.out.println("---CONNECTION CLOSED---");
                ss.close();
            } catch (IOException ex) {
                System.out.println("IOException from run() SSC");
            }
        }

        public boolean checkWinner(){
            if(m==20){
                System.out.println("Mr. X is the winner");
                System.out.println(mrXpn + " - " + mrXsn);
                return true;
            } else if(mrXPos.equals(cop1Pos) || mrXPos.equals(cop2Pos) || mrXPos.equals(cop3Pos)) {
                System.out.println("Cops are the winner");
                System.out.println(cop1pn + " - " + cop1sn);
                System.out.println(cop2pn + " - " + cop2sn);
                System.out.println(cop3pn + " - " + cop3sn);
                return true;
            } else {
                return false;
            }
        }

        public void sendMove(String s1, String s2, int n, int mrxp) {
            try {
                dataOut.writeInt(n);
                dataOut.flush();
                dataOut.writeUTF(s1);
                dataOut.flush();
                dataOut.writeUTF(s2);
                dataOut.flush();
                dataOut.writeInt(mrxp);
                dataOut.flush();
            } catch (IOException ex) {
                System.out.println("IOException from sendMove() SSC");
            }
        }

        public void closeConnection(){
            try{
                socket.close();
            } catch(IOException ex) {
                System.out.println("IOException from closeConnection() SSC");
            }
        }

        public void receiveParticipant(){
            try{
                String PName = dataIn.readUTF();
                String SName = dataIn.readUTF();
                int r = dataIn.readInt();
                if(r==0){
                    mrXpn = PName;
                    mrXsn = SName;
                } else if(r==1){
                    cop1pn = PName;
                    cop1sn = SName;
                } else if(r==2){
                    cop2pn = PName;
                    cop2sn = SName;
                } else {
                    cop3pn = PName;
                    cop3sn = SName;
                }
            } catch(IOException ex){
                System.out.println("IOException from receiveParticipant() SSC");
            }
        }

        public void receiveMoveAndPos() {
            try {
                int TURN = dataIn.readInt();
                    if (TURN == 0) {
                        String mrXMove = dataIn.readUTF();
                        mrXPos = dataIn.readUTF();
                        int MRXP = dataIn.readInt();
                            if (mrXPos.equals("none")) {
                                System.out.println("Mr. X used " + mrXMove);
                            } else {
                                m++;
                                if(MRXP == 1){
                                    m--;
                                }
                                System.out.println("Mr. X pressed " + mrXMove + ". Position - " + mrXPos);
                            }
                        client1.sendMove(mrXMove, mrXPos, TURN, MRXP);
                        client2.sendMove(mrXMove, mrXPos, TURN, MRXP);
                        client3.sendMove(mrXMove, mrXPos, TURN, MRXP);
                        client4.sendMove(mrXMove, mrXPos, TURN, MRXP);
                    } else if (TURN == 1) {
                        String cop1Move = dataIn.readUTF();
                        cop1Pos = dataIn.readUTF();
                        if (cop1Pos.equals("none")) {
                            System.out.println("Cop 1 used " + cop1Move);
                        } else {
                            System.out.println("Cop 1 pressed " + cop1Move + ". Position - " + cop1Pos);
                        }
                        client1.sendMove(cop1Move, cop1Pos, TURN, 0);
                        client2.sendMove(cop1Move, cop1Pos, TURN, 0);
                        client3.sendMove(cop1Move, cop1Pos, TURN, 0);
                        client4.sendMove(cop1Move, cop1Pos, TURN, 0);
                    } else if (TURN == 2) {
                        String cop2Move = dataIn.readUTF();
                        cop2Pos = dataIn.readUTF();
                        if (cop2Pos.equals("none")) {
                            System.out.println("Cop 2 used " + cop2Move);
                        } else {
                            System.out.println("Cop 2 pressed " + cop2Move + ". Position - " + cop2Pos);
                        }
                        client1.sendMove(cop2Move, cop2Pos, TURN, 0);
                        client2.sendMove(cop2Move, cop2Pos, TURN, 0);
                        client3.sendMove(cop2Move, cop2Pos, TURN, 0);
                        client4.sendMove(cop2Move, cop2Pos, TURN, 0);
                    } else if (TURN == 3) {
                        String cop3Move = dataIn.readUTF();
                        cop3Pos = dataIn.readUTF();
                        if (cop3Pos.equals("none")) {
                            System.out.println("Cop 3 used " + cop3Move);
                        } else {
                            System.out.println("Cop 3 pressed " + cop3Move + ". Position - " + cop3Pos);
                        }
                        client1.sendMove(cop3Move, cop3Pos, TURN, 0);
                        client2.sendMove(cop3Move, cop3Pos, TURN, 0);
                        client3.sendMove(cop3Move, cop3Pos, TURN, 0);
                        client4.sendMove(cop3Move, cop3Pos, TURN, 0);
                    }
            } catch (IOException ex) {
                System.out.println("IOException from receiveMoveAndPos() SSC");
            }
        }
    }

    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }

}
