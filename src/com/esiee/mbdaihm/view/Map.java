/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esiee.mbdaihm.view;

import com.esiee.mbdaihm.datamodel.DataManager;
import com.esiee.mbdaihm.datamodel.countries.Country;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author DorianTona
 */
public class Map extends JPanel implements MouseMotionListener, MouseWheelListener, MouseListener
{
    private final HashMap<String,ArrayList<GeneralPath>> countriesShapes = new HashMap<>();
    private float mouseX = 0;
    private float mouseY = 0;
    private int prevX = 0;
    private int prevY = 0;
    private int dragX = 0;
    private int dragY = 0;
    private float mouseScroll = 1;
    
    public HashMap<String, Color> hash = new HashMap();

    // --------------------------------------------
    // CONSTRUCTOR
    // --------------------------------------------
    public Map()
    {   
        
        JButton BoutonRecherche = new JButton("Rechercher");
        
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
        this.addMouseListener(this);
        
        GeneralPath t;

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
        
        for(String countryName : countriesShapes.keySet())
        {
        hash.put(countryName, new Color(169, 169, 169));
        }
        System.err.println("A");
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

        //drawCountries(g2d);
        drawCountriesIndicator(g2d, hash);
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
    
        public void drawCountriesIndicator(Graphics2D g2d, HashMap<String, Color> Hash)
    {           
        //dessin des pays
        for(String countryName : countriesShapes.keySet())
        {
            for(GeneralPath p : countriesShapes.get(countryName))
            {
                g2d.setPaint(Color.BLACK);
                g2d.draw(p);
                
                g2d.setPaint(Hash.get(countryName));
                g2d.fill(p);
                
                
                if(p.contains(mouseX, mouseY)) {
                    g2d.setPaint(Color.RED);
                    for(GeneralPath pp : countriesShapes.get(countryName))
                        g2d.fill(pp);
                }
            }
        }
    }
        
    public void setHashMap(HashMap<String, Color> newHash)
    {
       hash = newHash;
        System.err.println("Hash Changed");
       repaint();
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
    public void mousePressed(MouseEvent e) {
        prevX = e.getX();
        prevY = e.getY();
    }

    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
}
