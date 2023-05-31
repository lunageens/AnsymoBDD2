# Created by lunag at 17/05/2023
  # This is Gherkin file for BDD scenario
  @CompleteTest
Feature: Verify the Ansymo Web application

    @Exercise5
  # press ctrl to hover over steps and go to step definitions
  Scenario Outline: Verify all courses from homepages (names and professors)
    Given the user is on the Ansymo homepage
    When the user clicks the <linkText> link in the menu section
    Then the user should see a page with all the courses listed
    And for each course, there should be a page that is loaded and there should be a professor
    Examples:
      | linkText  |
      | "Courses" |

  @Exercise6
  Scenario: Verify Assignment Links in Software Testing Course
    Given the user is on the Software Testing course page
    When the user sees the links for each assignment
    Then the user should receive a warning when an assignment link doesn't exist
    And the user should verify the link format of each assignment link

  @Exercise7
  Scenario Outline: Verify Student Groups in Software Testing Course
    Given the user is on the Software Testing course page
    When the user says that a student <name> belongs to student group <number>
    Then the user should be in a student group
    And the user should receive a warning when he does not belong to that student group number
    Examples:
      | name         | number |
      | Luna Geens   | 1      |
      | Luna Geens   | 2      |
      | Luna Geens   | 3      |
      | Thimoty Smet | 1      |
      | Mieke Hans   | 2      |


  @Exercise8
  Scenario Outline: Verify Mandatory Presence of a student in Software Testing Course
    Given the user is on the Software Testing course page
    When the user says that a student <name> belongs to student group <number>
    Then the user should see his mandatory presence as presenter
    And the user should see his mandatory presence as opponent
    Examples:
      | name           | number |
      | "Luna Geens"   | 1      |
      | "Luna Geens"   | 2      |
      | "Luna Geens"   | 3      |
      | "Thimoty Smet" | 1      |

