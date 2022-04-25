package com.example.optalk_korpakolas;

import java.util.concurrent.Future;

public class CirclePacking {

    public CirclePacking (SecondFragment.CanvasView canvasView) {
        circleManager = new CircleManager();
        N = 0;
        stepSize = 0.25;
        this.canvasView = canvasView;
    }

    public void init(int N) {
        this.N = N;
        stepSize = 0.25;
        circleManager.genNRandCircle(N);
    }

    public void updateMinDistance() {
        minDistance = circleManager.calcGLobalMinDistance();
    }

    public double getMinDistance() {
        return minDistance;
    }

    public double getStepSize() {
        return stepSize;
    }

    public Circle getCircle(int i) {
        return circleManager.getCircle(i);
    }

    private boolean moveRight(Circle c) {
        double x = c.getX();
        if (x + stepSize > 1)
            return false;
        c.setX(x + stepSize);
        return true;
    }

    private boolean moveLeft(Circle c) {
        double x = c.getX();
        if (x - stepSize < 0)
            return false;
        c.setX(x - stepSize);
        return true;
    }

    private boolean moveDown(Circle c) {
        double y = c.getY();
        if (y + stepSize > 1)
            return false;
        c.setY(y + stepSize);
        return true;
    }

    private boolean moveUp(Circle c) {
        double y = c.getY();
        if (y - stepSize < 0)
            return false;
        c.setY(y - stepSize);
        return true;
    }

    private void moveAll() {
        double curDist, newDist;

        //Right;
        for (int i = 0; i < N; i++) {
            Circle c = circleManager.getCircle(i);
            curDist = circleManager.calculateMinDistance(c);
            if (moveRight(c)) {
                newDist = circleManager.calculateMinDistance(c);
                if (curDist > newDist) {
                    moveLeft(c);
                } else {
                    isPackingChanged = true;
                }
            }
        }

        //Down
        for (int i = 0; i < N; i++) {
            Circle c = circleManager.getCircle(i);
            curDist = circleManager.calculateMinDistance(c);
            if (moveDown(c)) {
                newDist = circleManager.calculateMinDistance(c);
                if (curDist > newDist) {
                    moveUp(c);
                } else {
                    isPackingChanged = true;
                }
            }
        }

        //Left
        for (int i = 0; i < N; i++) {
            Circle c = circleManager.getCircle(i);
            curDist = circleManager.calculateMinDistance(c);
            if (moveLeft(c)) {
                newDist = circleManager.calculateMinDistance(c);
                if (curDist > newDist) {
                    moveRight(c);
                } else {
                    isPackingChanged = true;
                }
            }
        }

        //Up
        for (int i = 0; i < N; i++) {
            Circle c = circleManager.getCircle(i);
            curDist = circleManager.calculateMinDistance(c);
            if (moveUp(c)) {
                newDist = circleManager.calculateMinDistance(c);
                if (curDist > newDist) {
                    moveDown(c);
                } else {
                    isPackingChanged = true;
                }
            }
        }
    }

    public void iterateMove() {
        isPackingChanged = true;
        while (isPackingChanged) {
            isPackingChanged = false;
            moveAll();
        }
    }

    public void iterate(double minStepSize) {
        while (stepSize > minStepSize) {
            iterateMove();
            stepSize = stepSize / 1.5;
        }
        updateMinDistance();
        canvasView.invalidate();
    }

    public void iterate() {
        iterate(0.00001);
    }

    public void iterateOnce() {
        if (stepSize < 1.0E-10) return;
        iterateMove();
        stepSize = stepSize / 1.5;
        updateMinDistance();
        canvasView.invalidate();
    }

    protected int N;
    protected double stepSize;
    protected double minDistance = 1000;
    protected boolean isPackingChanged = false;
    protected CircleManager circleManager;
    protected SecondFragment.CanvasView canvasView;
}
