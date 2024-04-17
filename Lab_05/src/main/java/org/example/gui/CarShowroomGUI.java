package org.example.gui;

import org.example.core.CarShowroom;
import org.example.core.CarShowroomContainer;

import javax.swing.*;
import java.awt.*;

public class CarShowroomGUI extends JFrame {
    private CarShowroomContainer carShowroomContainer;
    private CenterPanel centerPanel;
    private VehiclePanel vehiclePanel;
    //private CarShowroom defaultShowroom;

    public CarShowroomGUI() {
        this.setTitle("Lab_05");
        //this.setSize(800, 1200);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        //ImageIcon image = new ImageIcon("logo.jpg");
        //this.setIconImage(image.getImage());

        carShowroomContainer = new CarShowroomContainer();

        initComponents();

        this.setVisible(true);

        this.pack();
    }

    private void initComponents() {
        centerPanel = new CenterPanel(carShowroomContainer, this);
        vehiclePanel = new VehiclePanel(carShowroomContainer.getShowroom("Salon Warszawa"));  // Initialize with null or a default showroom

        this.add(centerPanel, BorderLayout.WEST);
        this.add(vehiclePanel, BorderLayout.CENTER);

    }

    public void updateVehiclePanel(String showroomName) {
        vehiclePanel.updateShowroom(carShowroomContainer.getShowroom(showroomName));
    }
}
