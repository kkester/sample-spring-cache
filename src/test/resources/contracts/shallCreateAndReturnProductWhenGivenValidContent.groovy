import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("""should create and return a product when called with valid product JSON""")
    request {
        method POST()
        url "/products"
        headers {
            contentType(applicationJson())
        }
        body(
            productName: value(c(anyUuid()), p('New Test Product')),
            releaseDate: anyDate(),
            imageUrl: anyUrl()
        )
    }
    response {
        status 201
        headers {
            contentType(applicationJson())
            location()
        }
        body(
            productId: $(anyNonBlankString()),
            productName: fromRequest().body('$.productName'),
            releaseDate: fromRequest().body('$.releaseDate'),
            imageUrl: fromRequest().body('$.imageUrl')
        )
    }
}
