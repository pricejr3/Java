
import java.io.IOException;

import javax.swing.JOptionPane;

public class DisplayGui {
    public Window wind;
    public DisplayGame displayGame;
    public ProgramProtocol runner;

    public DisplayGui(ProgramProtocol main) throws IOException {
        this.runner = main;
        wind = new Window(this);
        displayGame = new DisplayGame(this);

        wind.displayGame(displayGame);
        wind.setVisible(true);
        wind.resizingWindow();
        wind.repaint();
    }

  
    public void startupView() throws IOException {
    	displayGame.resetView();
        wind.playerExit(false);
       
    }

    public void startupMenu() {
    	wind.playerExit(false);
    }


    public void waitingForPlayerView() {
    	wind.playerExit(true);
    }

    public void waitingArsenalView() {
    	displayGame.waitingView();
        wind.displayBoard();
    }

    
    public void positionarsenalView() {
    	displayGame.placeShipsView();
        wind.notDisplayBoard();
    }

    public void botharsenalsRecievedView() throws IOException {
    	wind.notDisplayBoard();
    	displayGame.playView(runner.getEnemyarsenal());
    }

   
    public void chooseShotView() {
    	displayGame.chooseShotView();
    }

   
    public void waitingForShotView() {
    	displayGame.waitingForShotView();
    }

    public void recieveShot(TargetCoordinates target) throws IOException {
    	displayGame.getShot(target);
        chooseShotView();
    }


    public boolean confirmDialog(String msg) {
        int response = JOptionPane.showConfirmDialog(wind, msg, "",
                JOptionPane.YES_NO_OPTION);
        return response == JOptionPane.YES_OPTION;
    }

    public void createServer() throws IOException {
        runner.startServer();
        wind.playerExit(true);
    }

    public void createClient() {
    	
    	String host = JOptionPane.showInputDialog("Enter server IP Address: ");

        if (host != null && host.length() != 0) {
            runner.startClient(host);
            wind.playerExit(true);
        }
    }

    public void exitGUi() throws IOException {
        ProgramProtocol.endGame();
        startupView();
    }

    public void playGui(OceanShips arsenal) throws IOException {
        waitingArsenalView();
        runner.setShips(arsenal);
    }

    public void shootGui(TargetCoordinates target) {
        waitingForShotView();
        runner.fireShot(target);
    }

}
