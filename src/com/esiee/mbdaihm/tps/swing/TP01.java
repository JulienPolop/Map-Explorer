package com.esiee.mbdaihm.tps.swing.solution;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.util.Arrays;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * First experiments with Swing components and Layouts.
 */
public class TP01
{
    // --------------------------------------------
    // ATTRIBUTES
    // --------------------------------------------
    private final JFrame frame;

    private final JTextArea centerTextArea;

    private final AbstractButton[] topButtons;

    private final JLabel bottomLabel;

    private final JCheckBox leftCheckBox;

    private final JComboBox<?> rightComboBox;

    // --------------------------------------------
    // CONSTRUCTOR
    // --------------------------------------------
    /**
     * Create a new TP01 instance.
     */
    public TP01()
    {
        frame = new JFrame("TP 01");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        topButtons = new AbstractButton[]
        {
            new JButton("JButton"),
            new JRadioButton("JRadioButton"),
            new JToggleButton("JToggleButton"),
        };

        centerTextArea = new JTextArea("JTextArea");

        bottomLabel = new JLabel("JLabel");

        leftCheckBox = new JCheckBox("JCheckBox");
        leftCheckBox.setSelected(true);
        leftCheckBox.addItemListener(e -> centerTextArea.setEnabled(e.getStateChange() == ItemEvent.SELECTED));

        rightComboBox = new JComboBox<>(new String[]
        {
            "JCombobox choice 1", "JCombobox choice 2"
        });
    }

    // --------------------------------------------
    // METHODS
    // --------------------------------------------
    private void layoutComponents()
    {
        frame.setLayout(new BorderLayout());

        // Center
        frame.add(centerTextArea, BorderLayout.CENTER);

        // Top
        JPanel topPanel = new JPanel();
        Arrays.stream(topButtons).forEach(topPanel::add);
        frame.add(topPanel, BorderLayout.PAGE_START);

        // Bottom
        bottomLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        frame.add(bottomLabel, BorderLayout.PAGE_END);

        // Line start
        frame.add(leftCheckBox, BorderLayout.LINE_START);

        // Line end
        frame.add(rightComboBox, BorderLayout.LINE_END);
    }

    private void display()
    {
        frame.pack();
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
            TP01 tp = new TP01();
            tp.layoutComponents();
            tp.display();
        });
    }
}
