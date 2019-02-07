import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("""should create and return a products when called with valid products JSON""")
    request {
        method POST()
        url "/products"
        headers {
            contentType(applicationJson())
        }
        body(
            name: value(c(anyUuid()), p('New Test ProductEntity')),
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
            id: $(anyNonBlankString()),
            name: fromRequest().body('$.name'),
            releaseDate: fromRequest().body('$.releaseDate'),
            imageUrl: fromRequest().body('$.imageUrl')
        )
    }
}
