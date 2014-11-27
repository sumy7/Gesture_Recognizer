package test;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Gesture;
import main.Point;

public class TestGUI {
    private static final long serialVersionUID = 1L;

    private JFrame mainFrame = new JFrame();
    private MyCanvas canvas = new MyCanvas();

    private JButton button_resample = new JButton("resample");
    private JButton button_centroid = new JButton("centroid");
    private JButton button_translate = new JButton("translate");
    private JButton button_scale = new JButton("scale");
    private JButton button_rotate = new JButton("rotate");

    private Gesture gesture = new Gesture().add(new Point(1, 1))
            .add(new Point(100, 1)).add(new Point(100, 100))
            .add(new Point(1, 100)).add(new Point(-255, -255));

    private void buildGUI() {
        canvas.setSize(500, 500);
        mainFrame.add(BorderLayout.CENTER, canvas);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 1));
        panel.add(button_resample);
        panel.add(button_centroid);
        panel.add(button_translate);
        panel.add(button_scale);
        panel.add(button_rotate);

        button_resample.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gesture.resample();
                canvas.drawGesture(gesture);
            }
        });
        button_centroid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.drawGestureWithCenter(gesture, gesture.centroid());
            }
        });
        button_translate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gesture.translate();
                canvas.drawGestureWithCenter(gesture, gesture.centroid());
            }
        });
        button_scale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gesture.scaleTo(200, 200);
                canvas.drawGestureWithCenter(gesture, gesture.centroid());
            }
        });
        button_rotate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gesture.rotate();
                canvas.drawGestureWithCenter(gesture, gesture.centroid());
            }
        });

        mainFrame.add(BorderLayout.EAST, panel);
    }

    public TestGUI() {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildGUI();
        mainFrame.setSize(600, 600);
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new TestGUI();
    }

}
