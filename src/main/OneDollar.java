package main;

import java.util.ArrayList;
import java.util.Collection;
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

    public void add(Collection<Gesture> list) {
        for (Gesture g : list) {
            g.standardization(this.width, this.height);
            this.gesturelist.add(g);
        }
    }

    public void clear() {
        this.gesturelist.clear();
    }

    public int getSize() {
        return this.gesturelist.size();
    }

    public int match(Gesture gesture) {
        gesture.standardization(this.width, this.height);
        double minDis = ERROR_RATIO;
        int matchindex = -1;
        for (int i = 0; i < this.gesturelist.size(); i++) {
            Gesture g = this.gesturelist.get(i);
            double d = cosDistance(g, gesture);
            if (d < minDis) {
                minDis = d;
                matchindex = i;
            }
        }
        return matchindex;
    }

    public Gesture get(int index) {
        if (index >= this.gesturelist.size() || index < 0) {
            return null;
        }
        return this.gesturelist.get(index);
    }

    public List<Gesture> matchAll(List<Gesture> gesturelist) {
        return null;
    }

    private double cosDistance(Gesture ga, Gesture gb) {
        double[] va = ga.getVector();
        double[] vb = gb.getVector();
        double a = 0;
        double b = 0;

        int size = Math.min(va.length, vb.length);
        for (int i = 0; i < size; i += 2) {
            a += va[i] * vb[i] + va[i + 1] * vb[i + 1];
            b += va[i] * vb[i + 1] - va[i + 1] * vb[i];
        }

        double angle = Math.atan(b / a);
        return Math.acos(a * Math.cos(angle) + b * Math.sin(angle));
    }

    public static void main(String[] args) {

    }
}
