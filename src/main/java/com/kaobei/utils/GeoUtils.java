package com.kaobei.utils;

import com.kaobei.commons.Circle;
import com.kaobei.commons.Point;
import com.kaobei.commons.Pos;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GeoUtils {

    /**
     * 地球周长
     */
    private static double L = 6381372 * Math.PI * 2;
    /**
     * 平面展开后，x轴等于周长
     */
    private static double W = L;
    /**
     * y轴约等于周长一半
     */
    private static double H = L / 2;
    /**
     * 米勒投影中的一个常数，范围大约在正负2.3之间
     */
    private static double mill = 2.3;


    /**
     * 将经纬度转换成X和Y轴
     * 米勒投影算法
     */
    public static Point posParseToPoint(double lng, double lat) {
        // 将经度从度数转换为弧度
        double x = lng * Math.PI / 180;
        // 将纬度从度数转换为弧度
        double y = lat * Math.PI / 180;
        // 米勒投影的转换
        y = 1.25 * Math.log(Math.tan(0.25 * Math.PI + 0.4 * y));
        // 弧度转为实际距离
        x = (W / 2) + (W / (2 * Math.PI)) * x;
        y = (H / 2) - (H / (2 * mill)) * y;
        return new Point(x, y);
    }

    /**
     * xy轴转坐标
     */
    public static Pos pointPosToPos(double x, double y) {
        //实际距离 转为弧度
        x = (x - (W / 2)) / (W / (2 * Math.PI));
        y = -1 * (y - (H / 2)) / (H / (2 * mill));
        // 米勒投影的转换反转
        y = (Math.atan(Math.pow(Math.E, y / 1.25)) - 0.25 * Math.PI) / 0.4;
        //将经度从弧度转换为度数
        double lng = 180 / Math.PI * x;
        //将纬度从弧度转换为度数
        double lat = 180 / Math.PI * y;
        return new Pos(lng, lat);
    }


    public Point getRealTimePoint(Circle c1, Circle c2, Circle c3) {
        List<Point> points = new ArrayList<>();
        List<Point> points1 = c1.getIntersectPoints(c2);

        if (!points1.isEmpty()) {
            Point point = checkPoint(points1, c3);
            points.add(point);
        }
        List<Point> points2 = c1.getIntersectPoints(c3);


        if (!points2.isEmpty()) {
            Point point = checkPoint(points2, c3);
            points.add(point);
        }

        List<Point> points3 = c2.getIntersectPoints(c3);


        if (!points3.isEmpty()) {
            Point point = checkPoint(points3, c1);
            points.add(point);
        }


        if (points.isEmpty()) {
            return null;
        }
        double x = 0;
        double y = 0;

        for (Point point : points) {
            x += point.getX();
            y += point.getY();
        }
        x = x / points.size();
        y = y / points.size();

        return new Point(x, y);
//        return points.get(0);
    }


    public Point checkPoint(List<Point> points, Circle circle) {
        if (points.size() == 1) {
            return points.get(0);
        }
        Point p1 = points.get(0);
        Point p2 = points.get(1);

        Double distance1 = getDistance(p1, circle.getCircleCenter());
        Double distance2 = getDistance(p2, circle.getCircleCenter());

        if (distance1 > distance2) {
            return p2;
        }
        return p1;
    }


    public Double getDistance(Point p1, Point p2) {
        Double x = new Double(Math.abs(p1.getX() - p2.getX()));

        Double y = new Double(Math.abs(p1.getY() - p2.getY()));

        double distance = Math.sqrt(x * x + y * y);

        return distance;
    }
}


