/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esiee.mbdaihm.view;

import com.esiee.mbdaihm.Launch;
import com.esiee.mbdaihm.dataaccess.wdi.WDIDataDecoder;
import com.esiee.mbdaihm.datamodel.DataManager;
import com.esiee.mbdaihm.datamodel.countries.Country;
import com.esiee.mbdaihm.datamodel.indicators.Indicator;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.input.KeyCode;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicComboBoxUI;

/**
 *
 * @author DorianTona
 */
public class TopBar extends JPanel
{
    private ArrayList<String> categories;
    private ArrayList<Icon> icons; 
    public JComboBox combobox;
    private JSlider slider;
    public IndicatorExplorer subCategoryExplorer;
    public IndicatorExplorer indicatorExplorer;
    
    private int anneDeRechercheDemarage = 2015;
    private int anneDeRecherche = anneDeRechercheDemarage;
    public String currentIndicator = null;
    
    public Map map = null;
    
    //Constructor
    public TopBar()
    {
        //size & layout
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBorder(new MatteBorder(0, 0, 1, 0, Color.gray));
        //this.setPreferredSize(new Dimension(1280, 56));
        
        //Button Categories
        categories = new ArrayList<>();
        DataManager.INSTANCE.getIndicators().map(Indicator::getTopic).distinct().sorted().forEach((t) -> {
            categories.add(t);
        });
        
        categories.forEach((cat) -> {
            this.add(new TopButton(cat));
        });
        //Il Faudra rajouter les icones pour chaques catégories
        
        
        
        //barre de recherche
        JLabel labelRecherche = new JLabel("");
        labelRecherche.setBorder(BorderFactory.createEmptyBorder(0, 32, 0, 0));
        this.add(labelRecherche);
        
        DefaultComboBoxModel model = new DefaultComboBoxModel(new String[] {});
        combobox = new JComboBox(model);
        combobox.setEditable(true);
        combobox.setPreferredSize(new Dimension(256, 40));
        
        ((JTextField)combobox.getEditor().getEditorComponent()).addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
                combobox.setPopupVisible(true);
                combobox.showPopup();
                
                String querrytrue = ((JTextField)combobox.getEditor().getEditorComponent()).getText();
                
                //if(!querrytrue.equals("")) 
                //{
                    //combobox.setPopupVisible(false);
                    combobox.hidePopup();
                    //combobox.setPopupVisible(true);
                    combobox.showPopup();
                    
                    
                    if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    {
                        System.out.println("ENTER" );
                        combobox.hidePopup();
                    }
                    if (e.getKeyCode() == 40 || e.getKeyCode() == 38)
                    {
                        System.out.println("Bas ou Haut" );
                        ((JTextField)combobox.getEditor().getEditorComponent()).setText(querrytrue);
                    }
                    else{
                      
                      combobox.removeAllItems();
                      
                      String querry = querrytrue;
                      System.out.println("KEY" + querry );
                      DataManager.INSTANCE.getIndicators().map(Indicator::getName).filter(n-> n.contains(querry)).distinct().sorted().forEach(s -> combobox.addItem(s));
                      ((JTextField)combobox.getEditor().getEditorComponent()).setText(querry);
                    }
                    
                    currentIndicator = combobox.getSelectedItem().toString();
                //}
                
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
       
        this.add(combobox);
        
        //slider année
        JLabel labelAnnee = new JLabel("Année");
        labelAnnee.setBorder(BorderFactory.createEmptyBorder(0, 32, 0, 0));
        this.add(labelAnnee);
        
        JTextField anneeTextField = new JTextField(4);
                
        slider = new JSlider(1960, 2015, anneDeRechercheDemarage);
        slider.setPreferredSize(new Dimension(256, 40));
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                anneeTextField.setText(Integer.toString(slider.getValue()));
                anneDeRecherche = slider.getValue();
                System.out.println("CHOIX ANNEE: "+ anneDeRecherche);
            }
        });
        this.add(slider);
        
        anneeTextField.setPreferredSize((new Dimension(100, 30)));
        anneeTextField.setText(Integer.toString(anneDeRecherche));
        anneeTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int annee = Integer.parseInt(anneeTextField.getText());
                    
                    if(annee < 1960 || annee > 2015)
                    {  
                        anneeTextField.setBackground(Color.pink);
                    }
                    else{
                        anneeTextField.setBackground(Color.white);
                        anneDeRecherche = annee;
                        slider.setValue(annee);
                        System.out.println("CHOIX ANNEE: "+ anneDeRecherche);
                        
                    }
                    

                }
                catch( java.lang.NumberFormatException nbfe)
                {
                   anneeTextField.setBackground(Color.pink);
                    throw(nbfe);
                }
            }
        });
        this.add(anneeTextField);
        
        //Bouton Recherche
        JButton BoutonRecherche = new JButton("Rechercher");
        BoutonRecherche.setPreferredSize(new Dimension(110, 40));
        BoutonRecherche.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RedrawMap(currentIndicator, anneDeRecherche);
                System.out.println("Recherche: "+currentIndicator + " "+ anneDeRecherche);
                ((JTextField)combobox.getEditor().getEditorComponent()).setText(currentIndicator);
            }
        });
        this.add(BoutonRecherche);
    }
    
    
    private void RedrawMap(String IndicatorToSearch, int Annee)
    {
        System.out.println("On cherche: "+ IndicatorToSearch + " " + Annee);
        DataManager.INSTANCE.getIndicators().filter(n-> n.getName().contains(IndicatorToSearch)).map(Indicator::getCode).forEach(Indicator -> WDIDataDecoder.decode(Launch.WDI_FOLDER, Indicator));

        HashMap<String, Double> valeurParPays = new HashMap<>();
        double maxValue = Double.MIN_VALUE;
        double minValue = Double.MAX_VALUE;
        
        for(Country c : DataManager.INSTANCE.getCountries())
        {
               double resultatValeur = c.getValueForYear(Annee);
               //System.out.println(c.getName()+": "+ resultatValeur);
               valeurParPays.put(c.getName(), resultatValeur);
               
               if (resultatValeur > maxValue)
                   maxValue = resultatValeur;
               if (resultatValeur < minValue)
                   minValue = resultatValeur;
        }
        
        Color gris = new Color(169, 169, 169);
        
        Color bleuMin = new Color(185, 233, 255);
        Color bleuMax = new Color(4, 113, 164);
        
        Color bleuMinHSL = new Color(199, 100, 86);
        Color bleuMaxHSL = new Color(199, 100, 33);
        
        int H = 0;
        int Smax = 100;
        int Smin = 92;
        int B = 100;
        
        HashMap<String, Color> couleurParPays = new HashMap<>();
        
        //System.out.println("================================================================");
        
        for (String s:valeurParPays.keySet())
        {
            Double valeur = valeurParPays.get(s);
            String NomPays = s;
            
            if (Double.toString(valeur).equals("NaN"))
            {
                couleurParPays.put(NomPays, gris);
            }
            else
            {
                double S = (valeur/maxValue) * Smax;
                
                int ColorConvert = Color.HSBtoRGB(0.6f, (float)S/100, B/100);
                Color ColorToDraw = new Color(ColorConvert);
                couleurParPays.put(NomPays,ColorToDraw);
            }  
        }
        
        map.setHashMap(couleurParPays);
    }
    
    //inner class
    private class TopButton extends JButton {
        
        public TopButton(String categoryName, Icon icon)
        {
            this.setIcon(icon);
            this.setToolTipText(categoryName);
            this.setPreferredSize(new Dimension(40, 40));
            
            JFrame f1 = (JFrame) SwingUtilities.windowForComponent(this);
            
            //Point position = this.getLocationOnScreen();
            Rectangle bounds = this.getBounds();
            
            TopButton thisTopButton = this;
            
            this.addActionListener
            (
                new ActionListener() 
                {
                    @Override
                    public void actionPerformed(ActionEvent e) 
                    {
                        // create a dialog Box 
                        IndicatorExplorer popup = new IndicatorExplorer(f1, TopBar.this, categoryName);
                
                        popup.setVisible(true);
                        popup.setName(categoryName);
                        popup.show(f1, thisTopButton.getLocationOnScreen().x, thisTopButton.getLocationOnScreen().y + thisTopButton.getBounds().height);
                    }
                }
            ); 
        } 

        public TopButton(String tooltip) { this(tooltip, null); } 
    }
}
