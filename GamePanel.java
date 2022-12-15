import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements ActionListener{
    static final int SCREEN_WIDTH=600;
    static final int SCREEN_HEIGHT=600;
    //define how big we want the objects in the game
    static final int UNIT_SIZE=25; // how big the items are
    //calculate how many object we can have in the screen
    static final int GAME_UNITS=(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY=75; //THE HIGHER THE DELAY THE SLOWER THE GAME IS
    final int x[]=new int[GAME_UNITS];
    final int y[]=new int[GAME_UNITS];
    int bodyParts=6;
    int applesEaten=0;
    int applesX; //x coordinate for where the apple is located
    int applesY;

    char direction= 'R';
    boolean running=false;
    Timer timer;
    Random random;
    //constructor
    GamePanel(){
        random=new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame(){
        newApple(); //create new apple screen
        running=true;
        timer=new Timer(DELAY, this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(running) {
            //making it grid just to see easier things
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {//draw lines across game panel
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            g.setColor(Color.red);
            g.fillOval(applesX, applesY, UNIT_SIZE, UNIT_SIZE);

            //draw the snake
            //for loop to iterate through the snake's body parts
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) { //head
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else { // the snake's body
                   // g.setColor(new Color(45, 180, 0));
                    //set the body color to multicolor
                    //it's optional
                   g.setColor(new Color((random.nextInt(255)), random.nextInt(255),
                            random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }

            //draw the current score
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 35));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score " + applesEaten)) / 2,
                    g.getFont().getSize());
        }
        else{
                gameOver(g);
            }

    }
    public void newApple(){
        applesX=random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        applesY=random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move(){
        for(int i=bodyParts;i>0;i--)//shift bodyParts
        {
            x[i]=x[i-1];
            y[i]=y[i-1];

        }
        //change the direction of the snake
        switch (direction){
            case 'U':
                y[0]=y[0]-UNIT_SIZE; //going to the next position
                break;
            case 'D':
                y[0]=y[0]+UNIT_SIZE;
                break;

            case 'L':
                x[0]=x[0]-UNIT_SIZE;
                break;

            case 'R':
                x[0]=x[0]+UNIT_SIZE;
                break;


        }
    }

    public void checkApple(){ //check to see if we grabbed the apple
       //examine coordinates of the snake and those of the apple
        //x[0] the initial position of our snake's head
        //applesX x position of the apple

        if((x[0]==applesX)) {
            if (y[0] == applesY) {
                    bodyParts++;
                    applesEaten++;
                    newApple();
            }
        }

    }
    public void checkCollisions(){ // check to see if the head collides with the body
        for(int i=bodyParts;i>0;i--){
            if((x[0]==x[i])&&(y[0]==y[i])){//the head collides with the body
                running=false; //using it to trigger Game over method
            }
        }
        //check if head touches left boarder
        if(x[0]<0){
            running=false;
        }
        //check if head touches right boarder
        if(x[0]>SCREEN_WIDTH){
            running=false;
        }
        //check if head touches top boarder
        if(y[0]<0){
            running=false;
        }
        //check if head touches bottom boarder
        if(y[0]>SCREEN_HEIGHT){
            running=false;
        }
        //stop the timer
        if(!running)
        {
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        //display Score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 35));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score " + applesEaten)) / 2,
                g.getFont().getSize());
        //Game Over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 80));
        FontMetrics metrics2= getFontMetrics(g.getFont());
        g.drawString("Game Over!", (SCREEN_WIDTH-metrics2.stringWidth("Game Over!"))/2, SCREEN_HEIGHT/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            //if our game is running
            move();
            checkApple(); // check to see if we ran into the apple
            checkCollisions();
        }
        repaint();
    }
    //Create inner class
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction!='R'){
                        direction='L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction!='L'){
                        direction='R';
                    }break;
                case KeyEvent.VK_UP:
                    if(direction!='D'){
                        direction='U';
                    }break;
                case KeyEvent.VK_DOWN:
                    if(direction!='U'){
                        direction='D';
                    }break;

                case KeyEvent.VK_A:
                    if(direction!='R'){
                        direction='L';
                    }
                    break;
                case KeyEvent.VK_D:
                    if(direction!='L'){
                        direction='R';
                    }
                    break;
                case KeyEvent.VK_W:
                    if(direction!='D'){
                        direction='U';
                    }
                    break;
                case KeyEvent.VK_S:
                    if(direction!='U'){
                        direction='D';
                    }
                    break;
            }
        }
    }
}
