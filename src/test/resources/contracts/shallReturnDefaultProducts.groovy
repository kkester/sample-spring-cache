import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("""
should return a default name when called without any parameters
""")
    request {
        method GET()
        url "/products"
    }
    response {
        status 200
        headers {
            contentType(applicationJson())
        }
        body('''
            [
                {
                    "productId":"9cfae4f0-e5fc-4d91-be83-3656a2776931"
                },
                {
                    "productId": "56fb94e8-7e85-48a3-8f59-866ad16bdefa"
                }
            ]
       ''')
    }
}
