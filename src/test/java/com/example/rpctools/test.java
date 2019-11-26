package com.example.rpctools;

/**
 * Creat by ZhangXueRong on 2019/4/9
 */
public class test {

    static class Point<S>{
        private S x;
        private S y;

        public S getX() {
            return x;
        }

        public void setX(S x) {
            this.x = x;
        }

        public S getY() {
            return y;
        }

        public void setY(S y) {
            this.y = y;
        }
    }

    public static void main(String[] args){
        Point<Integer> point = new Point<>();
        point.setX(100);
        System.out.println(point.getX());

        Point<Float> floatPoint = new Point<>();
        floatPoint.setY(100.2f);
        System.out.println(floatPoint.getY());
    }


}
