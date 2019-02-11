Feature: the client can retrieve the store
  Scenario: client makes a call to GET a store representation
    When the client calls /store
    Then the client receives status code of 200
    And the client receives "products"
    And the client receives "banners"
    And the client receives "promotions"
    And the participant's products is cached
    And the participant's "banners" is cached in "offers"