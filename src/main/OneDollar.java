package main;

import java.util.ArrayList;
import java.util.List;

public class OneDollar {

    public static double ERROR_RATIO = 0.3;

    private List<Gesture> gesturelist = new ArrayList<Gesture>();

    private double width = 200.0;
    private double height = 200.0;

    public OneDollar() {
        this.width = 200.0;
        this.height = 200.0;
    }

    public OneDollar(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public void add(Gesture gesture) {
        gesture.standardization(this.width, this.height);
        this.gesturelist.add(gesture);
    }

    public Gesture match(Gesture gesture) {
        gesture.standardization(200, 200);
        double minDis = ERROR_RATIO;
        Gesture match = null;
        for (Gesture g : gesturelist) {
            double d = cosDistance(g, gesture);
            if (d < minDis) {
                minDis = d;
                match = g;
            }
        }
        return match;
    }

    public List<Gesture> matchAll(List<Gesture> gesturelist) {
        return null;
    }

    private double cosDistance(Gesture ga, Gesture gb) {
        double a = 0;
        double b = 0;
        List<Point> galist = ga.getList();
        List<Point> gblist = gb.getList();

        for (int i = 0; i < galist.size(); i++) {
            Point pa = galist.get(i);
            Point pb = gblist.get(i);

            a += pa.getX() * pb.getX() + pa.getY() * pb.getY();
            b += pa.getX() * pb.getY() - pa.getY() * pb.getX();
        }

        double angle = Math.atan(b / a);
        return Math.acos(a * Math.cos(angle) + b * Math.sin(angle));
    }

    public static void main(String[] args) {

    }
}
