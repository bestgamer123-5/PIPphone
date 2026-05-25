package com.pipphone.phone;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

public class CarPhoneHud {
    private final CarPhoneController controller;

    private final JLabel time = new JLabel();
    private final JLabel speed = new JLabel();
    private final JLabel gear = new JLabel();
    private final JLabel engine = new JLabel();
    private final JLabel appStatus = new JLabel("Open an app...");
    private final JProgressBar battery = new JProgressBar(0, 100);
    private final JProgressBar fuel = new JProgressBar(0, 100);

    public CarPhoneHud(CarPhoneController controller) {
        this.controller = controller;
    }

    public void show() {
        JFrame frame = new JFrame("PIP Phone HUD");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(420, 820));

        JPanel phone = new JPanel();
        phone.setLayout(new BoxLayout(phone, BoxLayout.Y_AXIS));
        phone.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        phone.setBackground(new Color(16, 20, 28));

        time.setForeground(Color.WHITE);
        time.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 34));
        speed.setForeground(new Color(129, 218, 255));
        speed.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
        gear.setForeground(Color.WHITE);
        engine.setForeground(Color.WHITE);
        appStatus.setForeground(new Color(179, 188, 204));

        JPanel stats = new JPanel(new GridLayout(2, 2, 12, 12));
        stats.setOpaque(false);
        stats.add(labeled("Gear", gear));
        stats.add(labeled("Engine", engine));
        stats.add(labeled("Battery", battery));
        stats.add(labeled("Fuel", fuel));

        JPanel appsGrid = new JPanel(new GridLayout(2, 4, 10, 10));
        appsGrid.setOpaque(false);
        for (PhoneApp app : controller.apps()) {
            JButton btn = new JButton("<html><center>" + app.icon() + "<br/>" + app.name() + "</center></html>");
            btn.setFocusPainted(false);
            btn.addActionListener(e -> {
                app.onOpen().run();
                appStatus.setText("Opened: " + app.name());
            });
            appsGrid.add(btn);
        }

        phone.add(time);
        phone.add(speed);
        phone.add(stats);
        phone.add(new JLabel(" "));
        phone.add(labeled("Apps", appsGrid));
        phone.add(new JLabel(" "));
        phone.add(labeled("Status", appStatus));

        frame.setContentPane(phone);
        frame.setVisible(true);

        Timer timer = new Timer(700, e -> refresh());
        timer.start();
        refresh();
    }

    private JPanel labeled(String label, java.awt.Component value) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel title = new JLabel(label);
        title.setForeground(new Color(179, 188, 204));
        panel.add(title);
        panel.add(value);
        return panel;
    }

    private void refresh() {
        time.setText(controller.time());
        speed.setText(controller.speedText());
        gear.setText(controller.gear());
        engine.setText(controller.engineOn() ? "ON" : "OFF");
        battery.setValue(controller.battery());
        battery.setStringPainted(true);
        fuel.setValue(controller.fuel());
        fuel.setStringPainted(true);
    }
}
