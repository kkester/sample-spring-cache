package io.pivotal.springcache.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductEntityRepository extends JpaRepository<ProductEntity, String> {

//    @Trace
//    ProductEntity findByProductNameIsLike(String name);

}
