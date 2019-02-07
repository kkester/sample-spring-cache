Feature: the client can retrieve the store
  Scenario: client makes a call to GET a store representation
    When the client calls /store
    Then the client receives status code of 200
    And the client receives "products"
    And the client receives "banners"
    And the client receives "promotions"