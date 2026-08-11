Feature: Purchase the order from sauce labs

  Background:
    Given I landed on ecommerce page

  Scenario Outline: Positive test of purchasing the order
    Given Logged in with username key "<name_key>" and password key "<password_key>"
    When I add product to cart
    And Checkout and submit order
    Then "Thank you for your order!" message is displayed

    Examples:
      | name_key        | password_key |
      | std_user_email  | password     |
      | visual_user     | password     |
