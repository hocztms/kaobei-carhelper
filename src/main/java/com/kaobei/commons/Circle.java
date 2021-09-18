package com.kaobei.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Circle {

    private double x;

    private double y;

    private double r;


    public Circle(Point point,Double r){
        this.x = point.getX();
        this.y = point.getY();
        this.r = r;
    }
    public List<Point> getIntersectPoints(Circle circle){


        double a,b,c;

        List<Point> points = new ArrayList<>();


        double delta = -1;



        //如果 y1!=y2
        if(y!=circle.getY()){

            //为了方便代入
            double A = (x*x - circle.getX()*circle.getX() +y*y - circle.getY()*circle.getY() + circle.getR()*circle.getR() - r*r)/(2*(y-circle.getY()));
            double B = (x-circle.getX())/(y-circle.getY());

            a = 1 + B * B;
            b = -2 * (x + (A-y)*B);
            c = x*x + (A-y)*(A-y) - r*r;

            //下面使用判定式 判断是否有解
            delta=b*b-4*a*c;

            if(delta >0)
            {
                Point point1 = new Point((-b+Math.sqrt(b*b-4*a*c))/(2*a),null);
                point1.setY(A - B*point1.getX());

                Point point2 = new Point((-b-Math.sqrt(b*b-4*a*c))/(2*a),null);
                point2.setY(A - B*point2.getX());
                points.add(point1);
                points.add(point2);
            }
            else if(delta ==0)
            {
                Point point = new Point(-b/(2*a),null);
                point.setY(A - B*point.getX());
            }
        }
        else if(x!=circle.getX()){

            //当y1=y2时，x的两个解相等

            double point_x = (x*x - circle.getX()*circle.getX() + circle.getY()*circle.getY() - r*r)/(2*(x-circle.getX()));

            a = 1 ;
            b = -2*y;
            c = y*y - r*r + (point_x-x)*(point_x-x);

            delta=b*b-4*a*c;

            if(delta >0)
            {

                Point point1 = new Point(point_x,(-b+Math.sqrt(b*b-4*a*c))/(2*a));
                Point point2 = new Point(point_x,(-b-Math.sqrt(b*b-4*a*c))/(2*a));
                points.add(point1);
                points.add(point2);
            }
            else if(delta ==0)
            {
                Point point = new Point(point_x,-b/(2*a));
                points.add(point);
            }
        }
        return points;
    }

    public Point getCircleCenter(){
        return new Point(x,y);
    }

    public void setCircle(Point circleCenter,Double r){
        this.x = circleCenter.getX();

        this.y = circleCenter.getY();

        this.r = r;
    }

}
