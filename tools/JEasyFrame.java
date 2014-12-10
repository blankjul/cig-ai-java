package tools;

import javax.swing.*;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * Frame for the graphics.
 * PTSP-Competition
 * Created by Diego Perez, University of Essex.
 * Date: 20/12/11
 */
public class JEasyFrame extends JFrame {

    /**
     * Main component of the frame.
     */
    public Component comp;
    
    public ArrayList<Point> markers = new ArrayList<>();

    /**
     * Constructor
     * @param comp Main component of the frame.
     * @param title Title of the window.
     */
    public JEasyFrame(Component comp, String title) {
        super(title);
        this.comp = comp;
        getContentPane().add(BorderLayout.CENTER, comp);
        pack();
        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        repaint();
    }
    
    
    @Override
	public void paint(Graphics g) {
    	super.paint(g);
    	for (Point p : markers) {
            g.setColor(Color.RED);
            int markerSize = 10;
            g.fillOval(p.x + 10, p.y + 10, markerSize, markerSize);
        }
    }
    
    

    /**
     * Closes this component.
     */
    public void quit()
    {
        System.exit(0);
    }
}

