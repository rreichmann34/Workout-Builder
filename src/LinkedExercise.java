///////////////////////////////////////////////////////////////////////////////
//
// Title: The LinkedExercise class creates a node in a singly linked list. It is given an exercise
// and a next exercise.
//
// Course: CS 300 Fall 2023
//
// Author: Remington Reichmann
// Email: rreichmann@wisc.edu
// Lecturer: Mouna Kacem
//
///////////////////////////////////////////////////////////////////////////////
//
// Persons: NONE
// Online Sources: NONE
//
///////////////////////////////////////////////////////////////////////////////

public class LinkedExercise {
  private Exercise exercise; // The exercise this node contains
  private LinkedExercise next; // The next node in the Singly Linked list

  /**
   * Constructor for a LinkedExercise with a next node as null
   * 
   * @param data the exercise to be contained in this node
   */
  public LinkedExercise(Exercise data) {
    exercise = data;
    next = null;
  }

  /**
   * Constructor for a LinkedExercise with a next node that is not null
   * 
   * @param data the exercise to be contained in this node
   * @param next the next node in the singly linked list
   */
  public LinkedExercise(Exercise data, LinkedExercise next) {
    exercise = data;
    this.next = next;
  }

  /**
   * Getter method for the exercise in this node
   * 
   * @return the exercise this node contains
   */
  public Exercise getExercise() {
    return exercise;
  }

  /**
   * Getter method for the next node in this list
   * 
   * @return the next node in this singly linked list
   */
  public LinkedExercise getNext() {
    return next;
  }

  /**
   * Sets the next node in the list
   * 
   * @param node the next node in the list
   */
  public void setNext(LinkedExercise node) {
    next = node;
  }

  @Override
  /**
   * Contains a string version of this node. The format is WorkoutType(all caps) + the
   * exercise.toString() + ->. If there is no next node, then END comes after this.
   * 
   * @return the string representation of this LinkedExercise
   */
  public String toString() {
    String output = "";
    output += exercise.toString() + " -> ";
    if (next == null) { // If this node is the end of the list, add END to output.
      output += "END";
    }

    return output;
  }
}
