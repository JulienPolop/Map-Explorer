package com.esiee.mbdaihm.view;

import com.esiee.mbdaihm.Launch;
import com.esiee.mbdaihm.datamodel.DataManager;
import com.esiee.mbdaihm.datamodel.countries.Country;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Use of Java2D API.
 */
public class Projet
{
    // --------------------------------------------
    // ATTRIBUTES
    // --------------------------------------------
    private final JFrame frame;
    private final DrawingPanel panel;

    // --------------------------------------------
    // CONSTRUCTOR
    // --------------------------------------------
    public Projet()
    {
        frame = new JFrame("https://www.youtube.com/watch?v=I5fenjzeh7g");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panel = new DrawingPanel();
        panel.addMouseMotionListener(panel);
        panel.addMouseWheelListener(panel);
        panel.addMouseListener(panel);
    }

    // --------------------------------------------
    // METHODS
    // --------------------------------------------
    private void layoutComponents()
    {
        frame.setLayout(new BorderLayout());
        frame.add(panel);
    }

    private void display()
    {
        frame.setBounds(0, 0, 1280, 720);
        frame.setVisible(true);
    }

    // --------------------------------------------
    // MAIN METHOD
    // --------------------------------------------
    public static void main(String[] args)
    {
        Launch.initData();
        SwingUtilities.invokeLater(() ->
        {
            Projet projet = new Projet();
            projet.layoutComponents();
            projet.display();
        });
    }

    // --------------------------------------------
    // INNER CLASSES
    // --------------------------------------------
    private static class DrawingPanel extends JPanel implements MouseMotionListener, MouseWheelListener, MouseListener
    {
        private HashMap<String,ArrayList<GeneralPath>> countriesShapes = new HashMap<>();
        private float mouseX = 0;
        private float mouseY = 0;
        private int prevX = 0;
        private int prevY = 0;
        private int dragX = 0;
        private int dragY = 0;
        private float mouseScroll = 1;

        // --------------------------------------------
        // CONSTRUCTOR
        // --------------------------------------------
        public DrawingPanel()
        {
            GeneralPath t;
            
            if(countriesShapes.size() != DataManager.INSTANCE.getCountries().size())
                for(Country c : DataManager.INSTANCE.getCountries())
                    for(com.esiee.mbdaihm.datamodel.countries.Polygon p : c.getGeometry().getPolygons())
                    {
                        //création du path
                        t = new GeneralPath();
                        for(int i=0 ; i<p.points.length ; i++)
                        {
                            if(i==0) {
                                t.moveTo(p.points[i].lon, p.points[i].lat);
                            } else {
                                t.lineTo(p.points[i].lon, p.points[i].lat);
                            }
                        }
                        t.closePath();

                        //Chaque pays a une liste de ses paths
                        ArrayList<GeneralPath> l;
                        if(!countriesShapes.containsKey(c.getName()))
                        {
                            l = new ArrayList<>();
                        } else {
                            l = countriesShapes.get(c.getName());
                            countriesShapes.remove(c.getName());
                        }
                        l.add(t);
                        countriesShapes.put(c.getName(), l);
                    }
        }
        
        // --------------------------------------------
        // METHODS
        // --------------------------------------------
        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(0.1f));
            // Transformation to place the (0,0) at the panel center
            g2d.translate(getWidth() / 2f + dragX, getHeight() / 2f + dragY);
            // Zoom in
            g2d.scale((getWidth() / 2f) / (160*mouseScroll), -(getHeight() / 2f) / (90*mouseScroll));
            
            drawCountries(g2d);
        }

        private void drawCountries(Graphics2D g2d)
        {           
            //dessin des pays
            for(String countryName : countriesShapes.keySet())
            {
                for(GeneralPath p : countriesShapes.get(countryName))
                {
                    g2d.setPaint(Color.BLACK);
                    g2d.draw(p);
                    if(p.contains(mouseX, mouseY)) {
                        g2d.setPaint(Color.RED);
                        for(GeneralPath pp : countriesShapes.get(countryName))
                            g2d.fill(pp);
                    }
                }
            }
        }

        // --------------------------------------------
        // INTERFACE IMPLEMENTATION METHODS
        // --------------------------------------------
        @Override
        public void mouseDragged(MouseEvent e) {
            dragX += e.getX()-prevX;
            dragY += e.getY()-prevY;
            
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e)
        {
            mouseX = (e.getX() - (getWidth() / 2f + dragX))* (160*mouseScroll)/(getWidth()/2f);
            mouseY = (e.getY() - (getHeight() / 2f + dragY))* -(90*mouseScroll)/(getHeight()/2f);

            repaint();
        }
        
        @Override
        public void mouseWheelMoved(MouseWheelEvent e)
        {
            if(e.getUnitsToScroll() > 0 && mouseScroll <= 1)
                mouseScroll += 0.1f;
            else if(mouseScroll > 0)
                mouseScroll -= 0.1f;
            
            repaint();
        }

        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {
            prevX = e.getX();
            prevY = e.getY();
        }

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }
}
