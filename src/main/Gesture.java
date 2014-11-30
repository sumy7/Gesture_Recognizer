package main;

import java.util.ArrayList;
import java.util.List;

public class Gesture {
    private static int RESAMPLE_SIZE = 40;
    private static double SCALE_RATIO = 0.2;

    private List<Point> path;
    private Point center;

    private double[] vector;

    private String name;

    public Gesture() {
        this.name = "";
        this.path = new ArrayList<Point>();
    }

    public Gesture(String name) {
        this.name = name;
        this.path = new ArrayList<Point>();
    }

    public Gesture(String name, List<Point> pathList) {
        if (pathList == null) {
            throw new IllegalArgumentException("参数列表为空");
        }
        if (pathList.size() == 0) {
            throw new IllegalArgumentException("路径个数为空");
        }
        this.name = name;
        this.path = pathList;
    }

    public Gesture(String name, Point[] pathList) {
        if (pathList == null) {
            throw new IllegalArgumentException("参数列表为空");
        }
        if (pathList.length == 0) {
            throw new IllegalArgumentException("路径个数为空");
        }
        this.name = name;
        this.path = new ArrayList<Point>();
        for (Point p : pathList) {
            this.path.add(p);
        }
    }

    public Gesture(String name, Integer[] pathList) {
        if (pathList == null) {
            throw new IllegalArgumentException("参数列表为空");
        }
        if (pathList.length == 0) {
            throw new IllegalArgumentException("路径个数为空");
        }
        if (pathList.length % 2 != 0) {
            throw new IllegalArgumentException("坐标不完整");
        }
        this.name = name;
        this.path = new ArrayList<Point>();
        for (int i = 0; i < pathList.length; i += 2) {
            Point p = new Point(pathList[i], pathList[i + 1]);
            this.path.add(p);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Gesture add(Point point) {
        this.path.add(point);
        return this;
    }

    public void clear() {
        this.path.clear();
    }

    public List<Point> getList() {
        return this.path;
    }

    public int size() {
        return this.path.size();
    }

    public void resample() {
        double pathlength = 0;
        for (int i = 1; i < this.path.size(); i++) {
            pathlength += len(this.path.get(i), this.path.get(i - 1));
        }
        double perlen = pathlength / RESAMPLE_SIZE;

        double nowx = this.path.get(0).getX();
        double nowy = this.path.get(0).getY();

        List<Point> newpath = new ArrayList<Point>();
        newpath.add(new Point((int) nowx, (int) nowy));

        double remindlen = perlen;
        for (int i = 1; i < this.path.size(); i++) {
            Point nowpoint = this.path.get(i);
            double nowlen = len(nowx, nowy, nowpoint.getX(), nowpoint.getY());
            while (nowlen >= remindlen) {
                double deltax = nowpoint.getX() - nowx;
                double deltay = nowpoint.getY() - nowy;
                nowx += remindlen / nowlen * deltax;
                nowy += remindlen / nowlen * deltay;
                newpath.add(new Point((int) nowx, (int) nowy));
                nowlen = len(nowx, nowy, nowpoint.getX(), nowpoint.getY());
                remindlen = perlen;
            }
            remindlen -= nowlen;
            nowx = nowpoint.getX();
            nowy = nowpoint.getY();
        }
        this.path = newpath;
    }

    public Point centroid() {
        if (this.path.size() == 0) {
            return new Point(0, 0);
        }
        int sumx = 0;
        int sumy = 0;
        for (Point p : this.path) {
            sumx += p.getX();
            sumy += p.getY();
        }
        sumx /= this.path.size();
        sumy /= this.path.size();
        this.center = new Point(sumx, sumy);
        return this.center;
    }

    public void translate(Point center) {
        for (Point p : this.path) {
            p.setX(p.getX() - center.getX());
            p.setY(p.getY() - center.getY());
        }
    }

    public void translate() {
        translate(this.center);
    }

    public void scaleTo(double width, double height) {
        double halfwidth = width / 2;
        double halfheight = height / 2;
        double maxx = Double.NEGATIVE_INFINITY;
        double maxy = Double.NEGATIVE_INFINITY;
        double minx = Double.POSITIVE_INFINITY;
        double miny = Double.POSITIVE_INFINITY;
        for (Point p : this.path) {
            if (p.getX() > maxx)
                maxx = p.getX();
            if (p.getY() > maxy)
                maxy = p.getY();
            if (p.getX() < minx)
                minx = p.getX();
            if (p.getY() < miny)
                miny = p.getY();
        }
        if (SCALE_RATIO > 0) {
            double longside = Math.max(maxx - minx, maxy - miny);
            double shortside = Math.min(maxx - minx, maxy - miny);
            boolean uniformly = (shortside / longside) < SCALE_RATIO;
            if (uniformly) {
                double scaleX = halfwidth / longside;
                double scaleY = halfheight / longside;
                scale(scaleX, scaleY);
                return;
            }
        }
        double scaleX = halfwidth / (maxx - minx);
        double scaleY = halfheight / (maxy - miny);
        scale(scaleX, scaleY);
    }

    public void scale(double scaleX, double scaleY) {
        for (Point p : this.path) {
            p.setX((int) (p.getX() * scaleX));
            p.setY((int) (p.getY() * scaleY));
        }
    }

    public void rotateBy(double radius) {
        double cos = Math.cos(radius);
        double sin = Math.sin(radius);
        for (Point p : this.path) {
            double qx = p.getX() * cos - p.getY() * sin;
            double qy = p.getX() * sin + p.getY() * cos;
            p.setX((int) qx);
            p.setY((int) qy);
        }
    }

    private double rotateAngle() {
        double iAngle = Math.atan2(this.path.get(0).getY(), this.path.get(0)
                .getX());
        double r = Math.PI / 4;
        double base = r * Math.floor((iAngle + r / 2) / r);
        return iAngle - base;
    }

    public void rotate() {
        rotateBy(-rotateAngle());
    }

    public double[] getVector() {
        vectorize();
        return this.vector;
    }

    public void standardization(double width, double height) {
        resample();
        centroid();
        translate();
        scaleTo(width, height);
        rotate();
    }

    private void vectorize() {
        double sum = 0.0;
        vector = new double[RESAMPLE_SIZE * 2];
        for (int i = 0; i < RESAMPLE_SIZE; i++) {
            Point p = this.path.get(i);
            vector[2 * i] = p.getX();
            vector[2 * i + 1] = p.getY();
            sum += p.getX() * p.getX() + p.getY() * p.getY();
        }
        double magnitude = Math.sqrt(sum);
        for (int i = 0; i < RESAMPLE_SIZE * 2; i++) {
            vector[i] /= magnitude;
        }
    }

    private double len(double ax, double ay, double bx, double by) {
        double tmpx = ax - bx;
        double tmpy = ay - by;
        double tmp = tmpx * tmpx + tmpy * tmpy;
        return Math.sqrt(tmp);
    }

    private double len(Point a, Point b) {
        return len(a.getX(), a.getY(), b.getX(), b.getY());
    }
}
