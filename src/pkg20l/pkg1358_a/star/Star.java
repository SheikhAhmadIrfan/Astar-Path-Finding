package pkg20l.pkg1358_a.star;
/**
 *
 * @author ahmad
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Star {
    private static final int GRID_SIZE = 30;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI(GRID_SIZE));
    }

    private static void createAndShowGUI(int gridSize) {
        JFrame frame = new JFrame("Star");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 700);
        StarPanel starPanel = new StarPanel(gridSize);
        frame.add(starPanel);
        frame.setVisible(true);
    }
}

class StarPanel extends JPanel {
    private final int nodeSize = 15;
    private final Color backgroundColor = Color.BLACK;
    private final Color nodeColor = Color.WHITE;
    NodePanel[][] n = new NodePanel[30][30];
    NodePanel start, end, block, current;
    ArrayList<NodePanel> openList  =new ArrayList<>();
    ArrayList<NodePanel> closeList  =new ArrayList<>();
    boolean goalReached=false;
    int c;
    

    public StarPanel(int gridSize) {
        this.setBackground(backgroundColor);
        this.setLayout(new GridLayout(gridSize, gridSize));
        Random random = new Random();
        this.addKeyListener(new KeyHandler(this));
        this.setFocusable(true);
        
        for (int col = 0; col < gridSize; col++) {
            for (int row = 0; row < gridSize; row++) {
                n[col][row] = new NodePanel(col, row);
                this.add(n[col][row]);
                if (random.nextInt(100) < 10) {
                    block(col, row);
                }
            }
        }
        start(0, 0);
        end(25, 29);
        heuristic();
    }

    public void start(int col, int row) {
        n[col][row].startNode();
        start = n[col][row];
        current = start;
    }

    public void end(int col, int row) {
        n[col][row].endNode();
        end = n[col][row];
    }

    public void block(int col, int row) {
        n[col][row].blockNode();
        block = n[col][row];
    }
    public void heuristic() {
        for (int col = 0; col < 30; col++) {
            for (int row = 0; row < 30; row++) {
                int dx = Math.abs(n[col][row].col - end.col);
                int dy = Math.abs(n[col][row].row - end.row);
                n[col][row].hcost = (int) Math.sqrt(dx * dx + dy * dy);
                int da = Math.abs(n[col][row].col - start.col);
                int db = Math.abs(n[col][row].row - start.row);
                n[col][row].gcost = da + db;
                n[col][row].fcost = n[col][row].hcost + n[col][row].gcost;
                n[col][row].setText("<html>"+n[col][row].fcost+"</html>");
                n[col][row].setMargin(new Insets(0, 0, 0, 0));
            }
        }
    }
    public void search(){
         while (!goalReached) {
            current.close();
            closeList.add(current);
            openList.remove(current);
            if(current.row-1>=0){
                if(n[current.col][current.row-1].open==false && n[current.col][current.row-1].close==false&&n[current.col][current.row-1].block==false){
                    n[current.col][current.row-1].open();
                    n[current.col][current.row-1].p = current;
                    openList.add(n[current.col][current.row-1]);
                }
            }
            if(current.row-1>=0 && current.col-1>=0){
                if(n[current.col-1][current.row-1].open==false && n[current.col-1][current.row-1].close==false&&n[current.col-1][current.row-1].block==false){
                    n[current.col-1][current.row-1].open();
                    n[current.col-1][current.row-1].p = current;
                    openList.add(n[current.col-1][current.row-1]);
                }
            }
            if(current.row-1>=0 && current.col+1<30){
                if(n[current.col+1][current.row-1].open==false && n[current.col+1][current.row-1].close==false&&n[current.col+1][current.row-1].block==false){
                    n[current.col+1][current.row-1].open();
                    n[current.col+1][current.row-1].p = current;
                    openList.add(n[current.col+1][current.row-1]);
                }
            }
            if(current.row+1<30 && current.col-1>=0){
                if(n[current.col-1][current.row+1].open==false && n[current.col-1][current.row+1].close==false&&n[current.col-1][current.row+1].block==false){
                    n[current.col-1][current.row+1].open();
                    n[current.col-1][current.row+1].p = current;
                    openList.add(n[current.col-1][current.row+1]);
                }
            }
            if(current.row+1<30 && current.col+1<30){
                if(n[current.col+1][current.row+1].open==false && n[current.col+1][current.row+1].close==false&&n[current.col+1][current.row+1].block==false){
                    n[current.col+1][current.row+1].open();
                    n[current.col+1][current.row+1].p = current;
                    openList.add(n[current.col+1][current.row+1]);
                }
            }
            if(current.col-1>=0){
                if(n[current.col-1][current.row].open==false && n[current.col-1][current.row].close==false&&n[current.col-1][current.row].block==false){
                    n[current.col-1][current.row].open();
                    n[current.col-1][current.row].p = current;
                    openList.add(n[current.col-1][current.row]);
                }
            }
            if(current.row+1<30){
                if(n[current.col][current.row+1].open==false && n[current.col][current.row+1].close==false&&n[current.col][current.row+1].block==false){
                    n[current.col][current.row+1].open();
                    n[current.col][current.row+1].p = current;
                    openList.add(n[current.col][current.row+1]);
                }
            }
            if(current.col+1<30){
                if(n[current.col+1][current.row].open==false && n[current.col+1][current.row].close==false&&n[current.col+1][current.row].block==false){
                    n[current.col+1][current.row].open();
                    n[current.col+1][current.row].p = current;
                    openList.add(n[current.col+1][current.row]);
                }
            }
            if (openList.isEmpty()) {
                for (int col = 0; col < 30; col++) {
                    for (int row = 0; row < 30; row++) {
                        n[col][row].noPath();
                    }
                }
                break;
            }
            int index=0;
            int max=99999;
            for(int i=0;i<openList.size();i++){
                if(openList.get(i).fcost < max){
                    index = i;
                    max=openList.get(i).fcost;      
                }
                else if(openList.get(i).fcost == max){
                    if(openList.get(i).gcost < openList.get(index).gcost){
                        index=i;
                    }
                }
            }
            current = openList.get(index);
            if(current == end){
                goalReached=true;
                NodePanel a=end;
                while(a!=start){
                    a=a.p;
                    if(a!=start){
                        a.path();
                    }
                }
            }
        }
    }
}

class NodePanel extends JButton{
    int col;
    int row;
    int hcost;
    int gcost;
    int fcost;
    NodePanel p;
    boolean start;
    boolean end;
    boolean block;
    boolean open;
    boolean close;
    public NodePanel(int col, int row) {
        this.col = col;
        this.row = row;
        setBackground(Color.white);
        setForeground(Color.black);
    }
    public void startNode() {
        setBackground(Color.blue);
        start = true;
    }
    public void noPath(){
        setBackground(Color.red);
    }
    public void endNode() {
        setBackground(Color.green);
        end = true;
    }
    public void blockNode() {
        setBackground(Color.black);
        block = true;
    }
    public void open(){
        open = true;
    }
    public void close(){
        close = true;
    }
    public void path(){
        setBackground(Color.gray);
    }
}


class KeyHandler implements KeyListener {
    StarPanel enterKeyListener;

    public KeyHandler(StarPanel enterKeyListener) {
        this.enterKeyListener = enterKeyListener;
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            enterKeyListener.search();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {}  

}