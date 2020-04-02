package com.esiee.mbdaihm.tps.swing.solution;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Use of Java2D API.
 */
public class TP03
{
    // --------------------------------------------
    // ATTRIBUTES
    // --------------------------------------------
    private final JFrame frame;

    private final DrawingPanel panel;

    // --------------------------------------------
    // CONSTRUCTOR
    // --------------------------------------------
    public TP03()
    {
        frame = new JFrame("TP03");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panel = new DrawingPanel();
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
        frame.setBounds(0, 0, 800, 600);
        frame.setVisible(true);
    }

    // --------------------------------------------
    // MAIN METHOD
    // --------------------------------------------
    /**
     * Main method.
     *
     * @param args the command line arguments, not used
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() ->
        {
            TP03 tp = new TP03();
            tp.layoutComponents();
            tp.display();
        });
    }

    // --------------------------------------------
    // INNER CLASSES
    // --------------------------------------------
    private static class DrawingPanel extends JPanel
    {
        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;

            // --------------------------------------------
            // Shape filling
            // --------------------------------------------
            // rectangle
            g2d.setPaint(Color.RED);
            g2d.fillRect(100, 100, 200, 100);

            // circle
            g2d.setPaint(Color.GREEN);
            g2d.fillOval(400, 100, 100, 100);

            // polygon
            Polygon polygon = new Polygon();
            polygon.addPoint(200, 300);
            polygon.addPoint(100, 400);
            polygon.addPoint(300, 400);

            g2d.setPaint(Color.BLUE);
            g2d.fillPolygon(polygon);

            // generalPath
            GeneralPath t = new GeneralPath();
            t.moveTo(400, 300);
            t.lineTo(550, 300);
            t.lineTo(550, 350);
            t.lineTo(500, 350);
            t.lineTo(500, 400);
            t.lineTo(450, 400);
            t.lineTo(450, 350);
            t.lineTo(400, 350);
            t.closePath();

            g2d.setPaint(Color.CYAN);
            g2d.fill(t);

            // --------------------------------------------
            // Shape drawing
            // --------------------------------------------
            g2d.setStroke(new BasicStroke(2));
            g2d.setPaint(Color.BLACK);

            // rectangle
            g2d.drawRect(100, 100, 200, 100);

            // circle
            g2d.drawOval(400, 100, 100, 100);

            // polygon
            g2d.drawPolygon(polygon);

            // generalPath
            g2d.draw(t);

            // --------------------------------------------
            // Transformations
            // --------------------------------------------
            g2d.setPaint(Color.MAGENTA);
            fillSquare(g2d);

            // change g2d transform
            g2d.translate(0, getHeight());
            g2d.scale(1, -1);
            fillSquare(g2d);

            // copy g2d transform
            AffineTransform tr = g2d.getTransform();
            tr.translate(getWidth(), 0);
            tr.scale(-1, 1);
            g2d.setTransform(tr);
            fillSquare(g2d);

            // set transform
            AffineTransform topRightTransform = AffineTransform.getTranslateInstance(getWidth(), 0);
            topRightTransform.concatenate(AffineTransform.getScaleInstance(-1, 1));
            g2d.setTransform(topRightTransform);
            fillSquare(g2d);
        }

        private void fillSquare(Graphics2D g2d)
        {
            g2d.fillRect(0, 0, 50, 50);
        }
    }
}
