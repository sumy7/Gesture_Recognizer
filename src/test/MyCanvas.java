package test;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import main.Gesture;
import main.Point;

public class MyCanvas extends Canvas {

    private int centerX;
    private int centerY;

    private Gesture gesture;
    private Point centerpoint;

    private boolean isdrawpath = false;
    private boolean isdrawcenter = false;

    public MyCanvas() {
    }

    private void drawLine(Graphics g, int x1, int y1, int x2, int y2) {
        g.drawLine(x1 + centerX, y1 + centerY, x2 + centerX, y2 + centerY);
    }

    @Override
    public void paint(Graphics gra) {
        centerX = this.getWidth() / 2;
        centerY = this.getHeight() / 2;
        Graphics2D g = (Graphics2D) gra;
        g.drawLine(0, 0, this.getWidth(), this.getHeight());
        g.drawLine(0, this.getHeight(), this.getWidth(), 0);
        g.drawLine(0, this.getHeight() / 2, this.getWidth(),
                this.getHeight() / 2);
        g.drawLine(this.getWidth() / 2, 0, this.getWidth() / 2,
                this.getHeight());

        if (isdrawpath) {
            List<Point> list = gesture.getList();
            Point a = list.get(0);
            if (isdrawcenter) {
                g.setPaint(Color.YELLOW);
                g.setStroke(new BasicStroke(8));
                drawLine(g, centerpoint.getX(), centerpoint.getY(), a.getX(),
                        a.getY());
                g.setPaint(Color.GREEN);
                g.setStroke(new BasicStroke(10));
                drawLine(g, centerpoint.getX(), centerpoint.getY(),
                        centerpoint.getX(), centerpoint.getY());
            }
            for (Point p : list) {
                g.setPaint(Color.BLUE);
                g.setStroke(new BasicStroke(8));
                drawLine(g, a.getX(), a.getY(), p.getX(), p.getY());
                g.setPaint(Color.RED);
                g.setStroke(new BasicStroke(10));
                drawLine(g, a.getX(), a.getY(), a.getX(), a.getY());
                a = p;
            }
            g.setPaint(Color.RED);
            g.setStroke(new BasicStroke(10));
            drawLine(g, a.getX(), a.getY(), a.getX(), a.getY());
        }
    }

    public void drawGesture(Gesture gesture) {
        this.gesture = gesture;
        this.isdrawcenter = false;
        this.isdrawpath = true;
        this.repaint();
    }

    public void drawGestureWithCenter(Gesture gesture, Point center) {
        this.gesture = gesture;
        this.centerpoint = center;
        this.isdrawcenter = true;
        this.isdrawpath = true;
        this.repaint();
    }

    public void clear() {
        this.isdrawcenter = false;
        this.isdrawpath = false;
        this.repaint();
    }

}
