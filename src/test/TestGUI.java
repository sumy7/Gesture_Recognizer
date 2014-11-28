package test;

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

    private static int CANVAS_HEIGHT = 500;
    private static int CANVAS_WIDTH = 500;
    private static int TOOLBAR_WIDTH = 100;

    private JFrame mainFrame = new JFrame("GR_Demo");
    private MyCanvas canvas = new MyCanvas();

    private JButton button_example = new JButton("example");

    private JButton button_resample = new JButton("resample");
    private JButton button_centroid = new JButton("centroid");
    private JButton button_translate = new JButton("translate");
    private JButton button_scale = new JButton("scale");
    private JButton button_rotate = new JButton("rotate");

    private void buildGUI() {
        mainFrame.setLayout(null);
        mainFrame.add(canvas);
        canvas.setBounds(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 1));
        panel.add(button_example);
        panel.add(button_resample);
        panel.add(button_centroid);
        panel.add(button_translate);
        panel.add(button_scale);
        panel.add(button_rotate);

        button_example.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.drawGesture(new Gesture().add(new Point(1, 1))
                        .add(new Point(100, 1)).add(new Point(100, 100))
                        .add(new Point(1, 100)).add(new Point(-255, -255)));
            }
        });

        button_resample.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.getGesture().resample();
                canvas.setIsDrawCenter(false);
                canvas.repaint();
            }
        });
        button_centroid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setIsDrawCenter(true);
                canvas.repaint();
            }
        });
        button_translate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.getGesture().translate();
                canvas.setIsDrawCenter(true);
                canvas.repaint();
            }
        });
        button_scale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.getGesture().scaleTo(CANVAS_WIDTH, CANVAS_HEIGHT);
                canvas.setIsDrawCenter(true);
                canvas.repaint();
            }
        });
        button_rotate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.getGesture().rotate();
                canvas.setIsDrawCenter(true);
                canvas.repaint();
            }
        });

        mainFrame.add(panel);
        panel.setBounds(CANVAS_WIDTH, 0, TOOLBAR_WIDTH, CANVAS_HEIGHT);
    }

    public TestGUI() {
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildGUI();
        mainFrame.setSize(CANVAS_WIDTH + TOOLBAR_WIDTH, CANVAS_HEIGHT);
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new TestGUI();
    }

}
