package view;

import br.ol.animation.bvh.Node;
import br.ol.animation.bvh.Skeleton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * View class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class View extends JPanel {
    
    private Skeleton skeleton;
    
    private double frameIndex;
    
    public View() {
        skeleton = new Skeleton("dancing.bvh");
        
        new Timer().scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                update();
                repaint();
            }
        }, 100, 1000 / 30);
    }
    
    private void update() {
        skeleton.setPose((int) frameIndex);
        //skeleton.setPose(null);
        frameIndex += 1;
        if (frameIndex > skeleton.getFrameSize() - 1) {
            frameIndex = 0;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(getWidth() / 2, getHeight() / 2);
        
        double scale = 10;
        
        for (int n = 0; n < skeleton.getNodes().size(); n++) {
            Node node = skeleton.getNodes().get(n);
            int x1 = (int) (scale * node.getPosition().getX());
            int y1 = (int) (-scale * node.getPosition().getY());
            g2d.setColor(Color.RED);
            g2d.fillOval((int) (x1 - 3), (int) (y1 - 3), 6, 6);
            
            g2d.setColor(Color.BLACK);
            // g2d.drawString(node.getName(), x1 + 10, y1);
            
            for (Node child : node.getChildrens()) {
                int x2 = (int) (scale * child.getPosition().getX());
                int y2 = (int) (-scale * child.getPosition().getY());
                g2d.drawLine(x1, y1, x2, y2);
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                View view = new View();
                JFrame frame = new JFrame();
                frame.setSize(800, 600);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.getContentPane().add(view);
                frame.setVisible(true);
                view.requestFocus();
            }
        });
    }
    
}
