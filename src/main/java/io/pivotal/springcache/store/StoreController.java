package io.pivotal.springcache.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping(name = "/offers")
    public Store getHomeResource() {
        return storeService.getHomeResource();
    }
}
