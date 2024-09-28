@maths @all
Feature: Perform mathematics operation


  @add
 Scenario Outline: Add two numbers
   Given Enter 'input1' as <a>
   And Enter 'input2' as <b>
   When Add the two number
   Then Perform operation 'Add' on 'input1' and 'input2' with <result>
   Examples:
     | a  | b | result |
     | 4  | 5 |   9    |
     | 3  | 3 |   6    |

  @sub
  Scenario Outline: Subtract two numbers
    Given Enter 'input1' as <a>
    And Enter 'input2' as <b>
    When Add the two number
    Then Perform operation 'Sub' on 'input1' and 'input2' with <result>
    Examples:
      | a  | b | result |
      | 10  | 5 |   5    |
      | 3  | 3 |   0    |