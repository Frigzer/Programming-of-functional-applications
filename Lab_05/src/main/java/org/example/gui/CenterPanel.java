package org.example.gui;

import org.example.core.CarShowroomContainer;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class CenterPanel extends JPanel {
    private JList<String> centersList;
    private DefaultListModel<String> centersListModel;
    private JButton addButton;
    private JButton removeButton;
    private JButton sortLoadButton;
    private CarShowroomGUI mainGui;

    public CenterPanel(CarShowroomContainer showroomContainer, CarShowroomGUI gui) {
        this.mainGui = gui;
        setLayout(new BorderLayout());
        initUI(showroomContainer);
        addListeners(showroomContainer);
    }

    private void initUI(CarShowroomContainer showroomContainer) {
        centersListModel = new DefaultListModel<>();
        centersList = new JList<>(centersListModel);
        JScrollPane scrollPane = new JScrollPane(centersList);

        addButton = new JButton("Add CarShowroom");

        removeButton = new JButton("Remove CarShowroom");

        sortLoadButton = new JButton("Sort Centers by Current Load");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(sortLoadButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load initial showrooms
        showroomContainer.getShowroomNames().forEach(centersListModel::addElement);
    }

    private void addListeners(CarShowroomContainer showroomContainer) {
        addButton.addActionListener(e -> addNewCenter(showroomContainer));
        removeButton.addActionListener(e -> removeSelectedCenter(showroomContainer));

        sortLoadButton.addActionListener(e -> {
            showroomContainer.sortShowroomsByLoad();
            refreshCenterList(showroomContainer);
        });

        centersList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = centersList.getSelectedValue();
                if (selected != null) {
                    mainGui.updateVehiclePanel(selected);
                }
            }
        });
    }

    private void addNewCenter(CarShowroomContainer showroomContainer) {
        String name = JOptionPane.showInputDialog(this, "Enter carShowroom name:");
        try {
            int capacity = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter max capacity:"));
            if(!Objects.equals(name, ""))
                showroomContainer.addCenter(name, capacity);
            refreshCenterList(showroomContainer);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Invalid input for capacity. Please enter a number.");
        }
    }

    private void removeSelectedCenter(CarShowroomContainer showroomContainer) {
        if(showroomContainer.getSize() > 1) {
            String selected = centersList.getSelectedValue();
            if (selected != null) {
                showroomContainer.removeCenter(selected);
                centersList.setSelectedValue(showroomContainer.getShowroomNames().getFirst(), false);

                refreshCenterList(showroomContainer);
            }
        }

    }

    private void refreshCenterList(CarShowroomContainer showroomContainer) {
        List<String> centers = showroomContainer.getShowroomNames();
        centersListModel.removeAllElements();
        centers.forEach(centersListModel::addElement);
    }
}
