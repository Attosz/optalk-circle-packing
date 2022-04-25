package com.example.optalk_korpakolas;

public class Circle {

    private static int num;

    public Circle () {
        num++;
        this.id = num;
        this.x = 0;
        this.y = 0;
    }

    public Circle (double x, double y) {
        num++;
        this.id = num;
        this.x = x;
        this.y = y;
    }

    public Circle (Circle c) {
        num++;
        this.id = num;
        this.x = c.x;
        this.y = c.y;
    }

    public void setCord (double x, double y) {
        setX(x);
        setY(y);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setMinDist(double minDist) {
        this.minDist = minDist;
    }

    public double getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getMinDist() {
        return minDist;
    }

    private int id;
    protected double x;
    protected double y;
    protected double minDist;

}
