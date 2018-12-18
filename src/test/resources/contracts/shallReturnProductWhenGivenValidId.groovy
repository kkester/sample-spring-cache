import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("""
should return a default name when called without any parameters
""")
    request {
        method GET()
        url "/products/1234567890"
    }
    response {
        status 200
        headers {
            contentType(applicationJson())
        }
        body(
            productId: $(anyNonBlankString())
        )
    }
}
