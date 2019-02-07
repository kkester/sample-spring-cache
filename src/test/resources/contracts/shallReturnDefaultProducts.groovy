import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("""
should return collection of products when called without any parameters
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
                    "id":"9cfae4f0-e5fc-4d91-be83-3656a2776931"
                },
                {
                    "id": "56fb94e8-7e85-48a3-8f59-866ad16bdefa"
                }
            ]
       ''')
    }
}
