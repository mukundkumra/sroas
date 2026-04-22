package restaurant.service;

import java.util.List;
import java.util.stream.Gatherers;

public class GathererService {

    public List<List<String>> windowMenuNames(List<String> names) {
        return names.stream()
                .gather(Gatherers.windowFixed(2))
                .toList();
    }
}
