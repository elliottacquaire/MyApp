package com.exple.ex_elli.myapp.animation;

import android.animation.TypeEvaluator;
import android.graphics.Point;


/**
 * 项目名称：MyApp
 * 类描述：
 * 创建人：ex-lixuyang
 * 创建时间：2017/9/24 16:24
 * 修改人：ex-lixuyang
 * 修改时间：2017/9/24 16:24
 * 修改备注：
 */

public class PointSinEvaluator implements TypeEvaluator {

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
//        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
//
//        float y = (float) (Math.sin(x * Math.PI / 180) * 100) + endPoint.getY() / 2;
//        Point point = new Point(x, y);
        return null;

    }
}
