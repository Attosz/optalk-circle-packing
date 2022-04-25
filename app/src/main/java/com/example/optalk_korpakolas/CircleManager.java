package com.example.optalk_korpakolas;

import java.util.ArrayList;
import java.util.Random;

public class CircleManager {

    final static long SEED = 1234;

    public CircleManager () {
        this.circles = new ArrayList<Circle>();
    }

    public void addCircle(Circle c) {
        circles.add(c);
    }

    public void createNCircle(int N) {
        for (int i = 0; i < N; i++) {
            addCircle(new Circle());
        }
    }

    public Circle getCircle (int i) {
        if (i >= 0 && i < circles.size())
            return circles.get(i);
        return null;
    }

    public void genNRandCircle (int N) {
        circles.clear();
        Random rand = new Random();
        //rand.setSeed(SEED);
        for (int i = 0; i < N; i++) {
            addCircle(new Circle(rand.nextDouble(), rand.nextDouble()));
        }
    }

    //For Testing invert transformation scaling
    public void genTestCircles () {
        circles.clear();
        Random rand = new Random();
        //rand.setSeed(SEED);
        double max = (double)1;
        double min = (double)0;
        addCircle(new Circle(min,max));
        addCircle(new Circle(max,min));
        addCircle(new Circle(0,0));
    }

    public double calculateDistance(Circle c1, Circle c2) {
        return Math.sqrt((c1.x - c2.x) * (c1.x - c2.x) + (c1.y - c2.y) * (c1.y - c2.y));
    }

    public double calculateMinDistance(Circle c) {
        double dist = 10000;
        double minDist = 10000;
        for (Circle cc : circles) {
            if (c.getId() == cc.getId()) continue;
            dist = calculateDistance(c,cc);
            if (minDist > dist) minDist = dist;
        }
        return minDist;
    }

    public double updateMinDistance(Circle c1, Circle c2) {
        double dist = Math.sqrt((c1.x - c2.x) * (c1.x - c2.x) + (c1.y - c2.y) * (c1.y - c2.y));
        if (c1.minDist > dist) c1.minDist = dist;
        if (c2.minDist > dist) c2.minDist = dist;
        return dist;
    }

    public double updateMinDistance(int c1, int c2) {
        return updateMinDistance(circles.get(c1), circles.get(c2));
    }

    public double calcGLobalMinDistance() {
        double minDist = 100000;
        double curDust = 0;
        for(int i = 0; i < circles.size()-1; i++) {
            for(int j = i+1; j < circles.size(); j++) {
                curDust = updateMinDistance(i,j);
                if (minDist > curDust)
                    minDist = curDust;
            }
        }
        /*
        for(Circle c : circles) {
            if (minDist > c.minDist)
                minDist = c.minDist;
        }
        */
        return minDist;
    }

    protected ArrayList<Circle> circles;

}
