/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esiee.mbdaihm.view;

import java.awt.Dimension;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JButton;

/**
 *
 * @author julie
 */
public class EntireView extends JPanel{
    
    JLayeredPane layeredPane = new JLayeredPane();
    
    
    public EntireView()
    {
        

        //layeredPane.setPreferredSize(new Dimension(this.getBounds().height,this.getBounds().width ));
        layeredPane.setPreferredSize(new Dimension(1000,1000));
        layeredPane.setVisible(true);
        
        
                //map
        Map map = new Map();
        //frame.add(map, BorderLayout.CENTER);
        
       //topbar
        TopBar topbar = new TopBar();
        //frame.add(topbar, BorderLayout.PAGE_START);
        
        topbar.map = map;
        


        
        //layeredPane.add(topbar, 1,0);
        //layeredPane.add(map, 2,0);
        //layeredPane.add(button, 3,0);
        
        
        
        //this.add(layeredPane);

        
    }


    
}
