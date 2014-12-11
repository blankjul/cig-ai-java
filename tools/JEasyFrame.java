package tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;

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
    
    public ArrayList<emergence.util.Pair<Point,Color>> markers = new ArrayList<>();
    
    public double blockSize = 0;

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
    	for (emergence.util.Pair<Point,Color> pair : markers) {
    		Point p = pair._1();
            g.setColor(pair._2());
            int markerSize = 10;
            g.fillOval(p.x , p.y , markerSize, markerSize);
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

