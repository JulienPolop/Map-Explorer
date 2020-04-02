package com.esiee.mbdaihm.tps.swing.solution;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Use of Java2D API.
 */
public class TP04
{
    // --------------------------------------------
    // ATTRIBUTES
    // --------------------------------------------
    private final JFrame frame;

    private final DrawingPanel panel;

    // --------------------------------------------
    // CONSTRUCTOR
    // --------------------------------------------
    public TP04()
    {
        frame = new JFrame("TP04");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panel = new DrawingPanel();
        panel.addMouseMotionListener(panel);
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
        frame.setBounds(0, 0, 600, 600);
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
            TP04 tp = new TP04();
            tp.layoutComponents();
            tp.display();
        });
    }

    // --------------------------------------------
    // INNER CLASSES
    // --------------------------------------------
    private static class DrawingPanel extends JPanel implements MouseMotionListener
    {
        private int mouseX = 0;

        private int mouseY = 0;

        // --------------------------------------------
        // METHODS
        // --------------------------------------------
        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            /* Draw in a frame of size 10x10, centred on the panel centre:
             * a grid
             * the polygons NW_POLYGON, NE_POLYGON, SW_POLYGON, SE_POLYGON
             * a circle of radius 2 in frame coordinates, centred on the mouse
             */

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(0.1f));

            // Transformation to place the (0,0) at the panel center
            g2d.translate(getWidth() / 2f, getHeight() / 2f);

            // Transformation to zoom in [-10; 10]
            g2d.scale((getWidth() / 2f) / 10, -(getHeight() / 2f) / 10);

            // Draw grid
            drawGrid(g2d);

            // Draw shapes
            drawPolygons(g2d);

            // Draw circle of radius 2 at mouse position
            drawCircle(g2d);
        }

        private void drawGrid(Graphics2D g2d)
        {
            AffineTransform savedTransform = g2d.getTransform();
            Stroke savedStroke = g2d.getStroke();

            // Axes
            drawHGridLine(g2d);
            drawVGridLine(g2d);

            // Grid
            Stroke gridStroke = new BasicStroke(0.02f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f,
                                                new float[]
                                                {
                                                    0.1f
                                                }, 0.05f);
            g2d.setStroke(gridStroke);

            g2d.translate(-10, 0);
            for (int i = 0; i < 20; i++)
            {
                g2d.translate(1, 0);
                drawVGridLine(g2d);
            }

            g2d.setTransform(savedTransform);
            g2d.translate(0, -10);
            for (int i = 0; i < 20; i++)
            {
                g2d.translate(0, 1);
                drawHGridLine(g2d);
            }

            // Restore g2d initial state
            g2d.setTransform(savedTransform);
            g2d.setStroke(savedStroke);
        }

        private void drawHGridLine(Graphics2D g2d)
        {
            g2d.drawLine(-10, 0, 10, 0);
        }

        private void drawVGridLine(Graphics2D g2d)
        {
            g2d.drawLine(0, -10, 0, 10);
        }

        private void drawPolygons(Graphics2D g2d)
        {
            g2d.setPaint(Color.RED);
            g2d.draw(NW_POLYGON);

            g2d.setPaint(Color.GREEN);
            g2d.draw(NE_POLYGON);

            g2d.setPaint(Color.BLUE);
            g2d.draw(SW_POLYGON);

            g2d.setPaint(Color.BLACK);
            g2d.draw(SE_POLYGON);
        }

        private void drawCircle(Graphics2D g2d)
        {
            AffineTransform transform = g2d.getTransform();
            Point2D.Float mouseWorld = new Point2D.Float();
            try
            {
                transform.inverseTransform(new Point2D.Float(mouseX, mouseY), mouseWorld);
            }
            catch (NoninvertibleTransformException e)
            {
                e.printStackTrace();
            }

            Ellipse2D.Float circle = new Ellipse2D.Float(mouseWorld.x - 1, mouseWorld.y - 1, 2, 2);
            g2d.draw(circle);
        }

        // --------------------------------------------
        // INTERFACE IMPLEMENTATION METHODS
        // --------------------------------------------
        @Override
        public void mouseDragged(MouseEvent e)
        {
            // Empty on purpose: nothing to do
        }

        @Override
        public void mouseMoved(MouseEvent e)
        {
            mouseX = e.getX();
            mouseY = e.getY();

            repaint();
        }

        // --------------------------------------------
        // CONSTANTS
        // --------------------------------------------
        private static final Polygon NW_POLYGON = new Polygon(
                new int[]
                {
                    -4, -4, -6
                },
                new int[]
                {
                    4, 6, 4
                },
                3);

        private static final Polygon NE_POLYGON = new Polygon(
                new int[]
                {
                    4, 4, 6
                },
                new int[]
                {
                    4, 6, 4
                },
                3);

        private static final Polygon SW_POLYGON = new Polygon(
                new int[]
                {
                    -4, -4, -6
                },
                new int[]
                {
                    -4, -6, -4
                },
                3);

        private static final Polygon SE_POLYGON = new Polygon(
                new int[]
                {
                    4, 4, 6
                },
                new int[]
                {
                    -4, -6, -4
                },
                3);
    }
}
