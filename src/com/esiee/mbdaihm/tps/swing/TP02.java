package com.esiee.mbdaihm.tps.swing.solution;

import java.awt.FlowLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Use of listeners in Swing.
 */
public class TP02
{
    // --------------------------------------------
    // ATTRIBUTES
    // --------------------------------------------
    private final JFrame frame;

    private final DefaultListModel<String> listModel;

    private final JList<String> list;

    private final JScrollPane listPane;

    private final JTextField entryTextField;

    private final JButton addButton;

    private final JLabel selectionLabel;

    private final JTextField selectedTextField;

    // --------------------------------------------
    // CONSTRUCTOR
    // --------------------------------------------
    public TP02()
    {
        frame = new JFrame("TP 02");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        entryTextField = new JTextField(8);

        addButton = new JButton("Add value");

        listModel = new DefaultListModel<>();

        list = new JList<>(listModel);

        list.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        listPane = new JScrollPane(list);

        selectionLabel = new JLabel("Selected value :");

        selectedTextField = new JTextField(8);
        selectedTextField.setEditable(false);
    }

    // --------------------------------------------
    // METHODS
    // --------------------------------------------
    private void initListeners()
    {
        addButton.addActionListener(e ->
        {
            String text = entryTextField.getText();
            if ((text != null) && !text.isEmpty())
            {
                listModel.addElement(text);
            }
        });

        list.addListSelectionListener(e -> selectedTextField.setText(list.getSelectedValue()));
    }

    private void layoutComponents()
    {
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        frame.add(entryTextField);
        frame.add(addButton);
        frame.add(listPane);
        frame.add(selectionLabel);
        frame.add(selectedTextField);
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
            TP02 tp = new TP02();
            tp.initListeners();
            tp.layoutComponents();
            tp.display();
        });
    }
}
