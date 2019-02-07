package io.pivotal.springcache.offers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Offer {

    private String name;
    private String description;
    private List<String> salesPitches;

}
