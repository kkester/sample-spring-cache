import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("""should return a default name when called without any parameters""")
    request {
        method GET()
        url "/products/56fb94e8-7e85-48a3-8f59-866ad16bdefa"
    }
    response {
        status 200
        headers {
            contentType(applicationJson())
        }
        body(
            id: '56fb94e8-7e85-48a3-8f59-866ad16bdefa',
            name: anyNonBlankString()
        )
    }
}
