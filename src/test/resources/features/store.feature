Feature: the client can retrieve the store
  Scenario: client makes a call to GET a store representation
    When the client calls /store
    Then the client receives status code of 200
    And the client receives "products" with "name" and a value of "Basic Model"
    And the client receives "products" with "name" and a value of "Standard Model"
    And the client receives "banners" with "name" and a value of "banner-1" when offers is "enabled"
    And the client receives "promotions" with "name" and a value of "banner-2" when offers is "enabled"
    And the participant's products is cached
    And the participant's "banners" is cached in "offers"
    And the participant's "promotions" is cached in "offers"