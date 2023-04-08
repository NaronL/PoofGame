package me.centrium.bossfight.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PercentUtils {

    public double getPercent(double current, double max){
        return (current * 100) / max;
    }
    
    public double getNumberByPercent(double current, double max){
        return (max / 100) * current;
    }

    public double getRawPercent(double current, double max){
        double percent = current / max;
        return percent < 0 ? 0 : percent > 1 ? 1 : percent;
    }
}
