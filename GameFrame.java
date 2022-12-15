import javax.swing.*;

public class GameFrame extends JFrame {
    GameFrame(){
       //This is equal GamePanel panel=new GamePanel() to the one that is written down;
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null); //to make it appear in the center of the display
    }
}
