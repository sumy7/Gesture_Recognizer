package test;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import main.Gesture;
import main.Point;

public class MyCanvas extends Canvas implements MouseListener,
        MouseMotionListener {

    private static long POINTCATCHER_TIMEINTERVAL = 20;

    private int centerX;
    private int centerY;

    private Gesture gesture = new Gesture();

    private boolean isdrawpath = false;
    private boolean isdrawcenter = false;

    private boolean isdrawing = false;

    private MouseEvent mouseEvent;

    private Runnable pointCatcherRunnable = new Runnable() {
        @Override
        public void run() {
            while (isdrawing) {
                try {
                    gesture.add(pointTransform(mouseEvent.getX(),
                            mouseEvent.getY()));
                    MyCanvas.this.repaint();
                    Thread.sleep(POINTCATCHER_TIMEINTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private Thread pointCatcherThread;

    public MyCanvas() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.isdrawing = false;
    }

    private void drawLine(Graphics g, int x1, int y1, int x2, int y2) {
        g.drawLine(x1 + centerX, y1 + centerY, x2 + centerX, y2 + centerY);
    }

    @Override
    public void paint(Graphics gra) {
        centerX = this.getWidth() / 2;
        centerY = this.getHeight() / 2;
        Point centerpoint = gesture.centroid();
        Graphics2D g = (Graphics2D) gra;
        g.drawLine(0, 0, this.getWidth(), this.getHeight());
        g.drawLine(0, this.getHeight(), this.getWidth(), 0);
        g.drawLine(0, this.getHeight() / 2, this.getWidth(),
                this.getHeight() / 2);
        g.drawLine(this.getWidth() / 2, 0, this.getWidth() / 2,
                this.getHeight());
        g.setPaint(Color.BLACK);
        g.setStroke(new BasicStroke(10));
        drawLine(g, 0, 0, 0, 0);

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

    public void setIsDrawCenter(boolean isdraw) {
        this.isdrawcenter = isdraw;
    }

    public void clear() {
        this.isdrawcenter = false;
        this.isdrawpath = false;
        this.gesture = new Gesture();
        this.repaint();
    }

    public Gesture getGesture() {
        return this.gesture;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.isdrawing = true;
        this.gesture = new Gesture();
        this.isdrawpath = true;
        this.isdrawcenter = false;
        this.pointCatcherThread = new Thread(pointCatcherRunnable);
        this.mouseEvent = e;
        this.pointCatcherThread.start();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.isdrawing = false;
        this.mouseEvent = e;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (isdrawing) {
            this.mouseEvent = e;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    private Point pointTransform(int x, int y) {
        return new Point(x - this.getWidth() / 2, y - this.getHeight() / 2);
    }
}
