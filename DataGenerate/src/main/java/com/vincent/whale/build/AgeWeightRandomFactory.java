package com.vincent.whale.build;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Vincent on 2018/1/24.
 */
public class AgeWeightRandomFactory {
    private static ArrayList<String> range;
    private static ArrayList<Double> weights;
    private static ArrayList<Integer> weightsDistribution = new ArrayList<>();
    static {
        range = new ArrayList<>();
        range.add("[10,18)");
        range.add("[18,30)");
        range.add("[30,50)");
        range.add("[50,65)");
        range.add("[65,75)");
        weights = new ArrayList<>();
        weights.add(0.15);
        weights.add(0.40);
        weights.add(0.30);
        weights.add(0.10);
        weights.add(0.05);
        weightsDistribution = getIntegerWeightsDistributionList(range, weights);
    }

    private static ArrayList<Integer> getIntegerWeightsDistributionList(ArrayList<String> range, ArrayList<Double> rangeWeights) {
        ArrayList<Integer> allNumbers = new ArrayList<>();
        ArrayList<Double> allWeights = new ArrayList<>();
        ArrayList<Integer> allTransformWeights = new ArrayList<>();
        double minWeights = 1.0;
        String tmp;
        for(int i=0,len=range.size(); i<len; i++) {
            tmp = range.get(i);
            tmp = tmp.substring(1, tmp.length()-1); //[x, y)表示一个Integer区间
            int start = Integer.valueOf(tmp.split(",")[0]), end = Integer.valueOf(tmp.split(",")[1]);
            double curWeight = rangeWeights.get(i)/(end-start);
            if(minWeights > curWeight)
                minWeights = curWeight;
            for(int j=start; j<end; j++) {
                allNumbers.add(j);
                allWeights.add(curWeight);
            }
        }
        double multiple = (1.0/minWeights)+1;
        for(Double weight : allWeights) {
            allTransformWeights.add((int)(weight*multiple));
        }
        ArrayList<Integer> weightsDistribution = new ArrayList<Integer>();
        for(int i=0,len=allNumbers.size(); i<len; i++) {
            for(int j=0; j<allTransformWeights.get(i); j++) {
                weightsDistribution.add(allNumbers.get(i));
            }
        }
        return weightsDistribution;
    }

    public static ArrayList<Integer> getIntegerWeightsDistributionList() {
        return weightsDistribution;
    }

    public static int getWeightedRandom(List<Integer> weightsDistribution) {
        int randomNum = ThreadLocalRandom.current().nextInt(0, weightsDistribution.size());
        return weightsDistribution.get(randomNum);
    }

    public static void main(String[] args) {
        ArrayList<Integer> list = getIntegerWeightsDistributionList();
        int[] statistic = new int[range.size()];
        for(int i=0;i<100;i++) {
            int num = getWeightedRandom(list);
            if(num>=10 && num<18) {
                statistic[0]++;
            }else if(num>=18 && num<30) {
                statistic[1]++;
            }else if(num>=30 && num<50) {
                statistic[2]++;
            }else if(num>=50 && num<65) {
                statistic[3]++;
            }else if(num>=65 && num<75) {
                statistic[4]++;
            }
        }
        for(int i=0;i<statistic.length;i++) {
            System.out.println("i: "+i+", nums: "+statistic[i]);
        }
    }
}
