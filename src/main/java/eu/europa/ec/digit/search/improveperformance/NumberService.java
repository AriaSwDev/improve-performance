package eu.europa.ec.digit.search.improveperformance;

import static java.util.stream.Collectors.toList;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NumberService {

    private static final int SAMPLE_SIZE = 100_000;
    private Random random = new Random();

    public Integer findSmallestDuplicate(List<Integer> data) {

        List<Integer> duplicates = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {

            for (int j = i + 1; j < data.size(); j++) {

                if (data.get(i).equals(data.get(j))) {

                    log.info("found duplicate {}", data.get(j));
                    duplicates.add(data.get(j));
                }
            }
        }

        return duplicates.stream().sorted().findFirst().orElse(null);

    }

    public Integer findSmallestDuplicateImproved(List<Integer> data) {

        Set<Integer> numbers = new HashSet<>();
//      List<Integer> duplicates = new ArrayList<>();

/*
        // Test passed 10 or 11ms
        for (int i = 0; i < data.size(); i++) {
            if( !numbers.add( data.get(i) ) ) {
                log.info("found duplicate {}", data.get(i));
                duplicates.add( data.get(i) );
            }
        }

        return duplicates.stream().sorted().findFirst().orElse(null);
 */
/*
        // Test failed due to the time taken >= 16ms

        for ( Integer i : data ) {
            if( !numbers.add( i ) ) {
                log.info("found duplicate {}", data.get(i));
                duplicates.add( data.get(i) );
            }
        }
*/
        // Test passed 8 to 9ms, like a further 1 to 2 ms improvement is speed.

        return data.stream()
                .filter(n -> !numbers.add(n))
                .collect(Collectors.toSet())
                .stream().sorted().findFirst().orElse(null);

    }


    public List<Integer> generateData() {

        List<Integer> data = IntStream.range(0, SAMPLE_SIZE).boxed().collect(toList());
        
        data.add(data.get(random.nextInt(data.size())));
        log.info("first duplicate number is: {}", data.get(data.size() - 1));
        data.add(data.get(random.nextInt(data.size())));
        log.info("second duplicate number is: {}", data.get(data.size() - 1));
        Collections.shuffle(data);

        return data;
    }
}
