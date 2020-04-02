/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esiee.mbdaihm.view;

import com.esiee.mbdaihm.datamodel.DataManager;
import com.esiee.mbdaihm.datamodel.indicators.Indicator;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**
 *
 * @author julie
 */
public class IndicatorExplorer extends JPopupMenu{
    
    private String Category;
    private String SubCategory;
    private JFrame Frame;
    private TopBar TopBar;
    
    public IndicatorExplorer(JFrame frame,TopBar topbar, String category)
    {
        Frame = frame;
        TopBar = topbar;
        
        if (topbar.subCategoryExplorer != null)
        {
            topbar.subCategoryExplorer.setVisible(false);
        }
        topbar.subCategoryExplorer = this;
        
        IndicatorExplorer thisIndicatorExplorer = this;
        JMenuItem mnuTitle = new JMenuItem(category );
        this.add(mnuTitle);
        this.addSeparator();
        
        ArrayList<String> subCategoryList = new ArrayList<>();
            DataManager.INSTANCE.getIndicatorsMap().forEach((categoryKey, subCateforyMap) -> {
                if (categoryKey.equals(category))
                {
                    subCateforyMap.forEach((subCategoryKey, IndicatorList) -> {
                    
                        JMenuItem subCat2Item = new JMenuItem(subCategoryKey);
                        subCat2Item.addActionListener
                        (
                            new ActionListener() 
                            {
                                @Override
                                public void actionPerformed(ActionEvent e) 
                                {
                                }
                            }
                        );

                        subCat2Item.addMouseListener
                        (
                            new MouseListener() 
                            { //i use this to apply the mouse event
                                @Override
                                public void mouseEntered(MouseEvent e) 
                                {
                                    OnMouseEnterItem(subCat2Item);
                                }

                                @Override
                                public void mouseExited(MouseEvent e) {
                                    OnMouseExitItem(subCat2Item);
                                }

                                @Override
                                public void mouseClicked(MouseEvent e) {  
                                    IndicatorExplorer popup = new IndicatorExplorer(Frame, topbar,category ,subCategoryKey);
                                    popup.show(frame, thisIndicatorExplorer.getLocationOnScreen().x + thisIndicatorExplorer.getBounds().width, subCat2Item.getLocationOnScreen().y);
                                }

                                @Override
                                public void mousePressed(MouseEvent e) {
                                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                }

                                @Override
                                public void mouseReleased(MouseEvent e) {
                                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                }
                            }
                        );


                        this.add(subCat2Item);
                    
                    });
                }
	});
    }
    
    public IndicatorExplorer(JFrame frame, TopBar topbar,String category, String subCategory)
    {
        Frame = frame;
        TopBar = topbar;
        
        if (topbar.indicatorExplorer != null)
        {
            topbar.indicatorExplorer.setVisible(false);
        }
        topbar.indicatorExplorer = this;
        
        JMenuItem mnuTitle = new JMenuItem(subCategory);
        this.add(mnuTitle);
        this.addSeparator();
        
        ArrayList<String> IndicatorList = new ArrayList<>();
        DataManager.INSTANCE.getIndicatorsMap().forEach((categoryKey, subCategoryMap) -> {
            
            if (category.equals(categoryKey))
            {
                subCategoryMap.forEach((subCategoryKey,indicatorList) -> {
                
                if (subCategoryKey.equals(subCategory))
                {
                    indicatorList.forEach((Indicator) -> {
                    
                    JMenuItem IndicatorItem = new JMenuItem(Indicator.getName());
                    IndicatorItem.addActionListener
                    (
                        new ActionListener() 
                        {
                            @Override
                            public void actionPerformed(ActionEvent e) 
                            {
                            }
                        }
                    );

                    IndicatorItem.addMouseListener
                    (
                        new MouseListener() 
                        { //i use this to apply the mouse event
                            @Override
                            public void mouseEntered(MouseEvent e) 
                            {
                                OnMouseEnterItem(IndicatorItem);
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {
                                OnMouseExitItem(IndicatorItem);
                            }

                            @Override
                            public void mouseClicked(MouseEvent e) {
                                ((JTextField)topbar.combobox.getEditor().getEditorComponent()).setText(Indicator.getName());
                                topbar.currentIndicator = Indicator.getName();
                                topbar.subCategoryExplorer.setVisible(false);
                                topbar.indicatorExplorer.setVisible(false);
                                
                                topbar.subCategoryExplorer = null;
                                topbar.indicatorExplorer = null;
                            }

                            @Override
                            public void mousePressed(MouseEvent e) {
                                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }

                            @Override
                            public void mouseReleased(MouseEvent e) {
                                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }
                        }
                    );


                    this.add(IndicatorItem);
                    
                    
                    });
                }
            }); 
                
            }
            

        });
    }
    
    private void OnMouseEnterItem(JMenuItem menuItem)
    {
        menuItem.setSelected(true);
        menuItem.setBackground(Color.LIGHT_GRAY);
    }
    
    private void OnMouseExitItem(JMenuItem menuItem)
    {
        
        menuItem.setSelected(false);
        menuItem.setBackground(getBackground());
    }
}
