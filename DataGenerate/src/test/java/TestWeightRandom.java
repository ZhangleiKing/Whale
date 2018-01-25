/**
 * Created by Vincent on 2018/1/24.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class TestWeightRandom {

    public static void main(String[] args) {
        System.out.println(1/0.0123);

//        for (int i = 0; i < 1000; i++) {
//            int index = getWeightedRandom(weights);
//            indexCount[index]++;
//            System.out.println(index);
//        }
//
//        System.out.println("indexCount: ");
//        for (int i = 0; i < weights.size(); i++) {
//            System.out.println(i + ": " + indexCount[i]);
//        }
    }

    private static int getWeightedRandom(List<Integer> weights) {
        List<Integer> weightsDistribution = new ArrayList<Integer>();
        for (int i = 0; i < weights.size(); i++) {
            for (int j = 0; j < weights.get(i); j++) {
                weightsDistribution.add(i);
            }
        }

        System.out.println("weightsDistribution size: "+weightsDistribution.size());

        int randomNum = ThreadLocalRandom.current().nextInt(0, weightsDistribution.size());
        return weightsDistribution.get(randomNum);
    }
}
