package io.pivotal.springcache.offers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Region(name = "offers")
public class Offer {

    private String name;
    private String description;
    private List<String> salesPitches;

}
