package sportsmate.menus;

import java.util.NoSuchElementException;

public class PersonalTeamMenu extends AbstractMenu {
  /**
   * Displays Personal Team menu.
   */
  @Override
  public String[] displayMenu() {
          getPrompt("\nEnter 1 to Create a Personal Match\nEnter 2 to Join a Personal Match"
          + "\nEnter 3 to Exit\n");

    try {
       do {
          System.out.printf("%n> ");
          selection = getScanner().next();
       } while ((!selection.equals("1")) && (!selection.equals("2")) && (!selection.equals("3")));
    } catch (NoSuchElementException noSuchElementException) {
        System.err.println("Invalid input. Terminating.");
      }

    String[] str = {selection};
    return str;
  }
}
