package com.tom;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSplayTreeWithSize {

  private static final Logger log = LoggerFactory.getLogger(TestSplayTreeWithSize.class);

  SplayTreeWithSize<String> tree;

  String[] values = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

  @Before
  public void setup() {
    tree = SplayTreeWithSize.create();
    Arrays.stream(values).forEach(tree::insert);
  }

  @Test
  public void testSmallContainer() {
    // make a Container and add all the nodes
    tree = SplayTreeWithSize.create();
    tree.insert("A");
    System.err.println(tree.printTree("Appended A"));
    //    SplayTreeWithSizePrinter.print(tree);
    tree.insert("B");
    System.err.println(tree.printTree("Appended B"));
    //    SplayTreeWithSizePrinter.print(tree);
    tree.insert("C");
    System.err.println(tree.printTree("Appended C"));
    //    SplayTreeWithSizePrinter.print(tree);
    //    tree.insert("D");
    //    System.err.println(tree.printTree("Appended D"));
    //    SplayTreeWithSizePrinter.print(tree);
    tree.insert("E");
    System.err.println(tree.printTree("Appended E"));
    //    SplayTreeWithSizePrinter.print(tree);
    tree.insert("F");
    System.err.println(tree.printTree("Appended F"));
    //    SplayTreeWithSizePrinter.print(tree);

    tree.splay("D");
    System.err.println(tree.printTree("Splayed D"));
    //    SplayTreeWithSizePrinter.print(tree);
    tree.splay("C");
    System.err.println(tree.printTree("Splayed C"));
    //    SplayTreeWithSizePrinter.print(tree);
    tree.splay("B");
    System.err.println(tree.printTree("Splayed B"));
    //    SplayTreeWithSizePrinter.print(tree);
    tree.splay("A");
    System.err.println(tree.printTree("Splayed A"));
    //    SplayTreeWithSizePrinter.print(tree);

    tree.splay("C");
    System.err.println(tree.printTree("Splayed C"));
    //    SplayTreeWithSizePrinter.print(tree);

    tree.insert("D");
    System.err.println(tree.printTree("Inserted D"));

    tree.append("H");
    //    tree.insert("H");
    System.err.println(tree.printTree("Appended H"));

    tree.append("G");
    //    tree.insert("H");
    System.err.println(tree.printTree("Appended G"));

    SplayTreeWithSize.Node<String> found = tree.find(3);
    System.err.println(tree.printTree("found " + found.key + " at " + 3));

    found = tree.find(5);
    System.err.println(tree.printTree("found " + found.key + " at " + 5));

    for (int i = 0; i < tree.size(); i++) {
      found = tree.find(i);
      System.err.println("found " + found.key + " at " + i);
    }

    tree.splay("C");
    System.err.println(tree.printTree("Splayed C"));

    for (int i = 0; i < tree.size(); i++) {
      found = tree.find(i);
      System.err.println("found " + found.key + " at " + i);
    }

    tree.validate();
  }

  @Test
  public void testSplit() {
    tree = SplayTreeWithSize.create();
    for (char c = 'A'; c <= 'Z'; c++) {
      tree.append("" + c);
    }
    System.err.println(tree.printTree("starting tree"));

    SplayTreeWithSize<String> newTree = tree.split("M");
    System.err.println(newTree.printTree("split off tree"));

    newTree.validate();

    System.err.println(tree.printTree("tree after split"));
    tree.validate();

    tree.join(newTree);
    System.err.println(tree.printTree("Joined Tree"));
    tree.validate();
  }

  @Test
  public void testSplitToPair() {
    tree = SplayTreeWithSize.create();
    for (char c = 'A'; c <= 'Z'; c++) {
      tree.append("" + c);
    }
    System.err.println(tree.printTree());

    Pair<SplayTreeWithSize<String>> pair = SplayTreeWithSize.split(tree, "M");
    System.err.println(pair.first.printTree());

    pair.first.validate();

    System.err.println(pair.second.printTree());

    pair.second.validate();
  }

  /**
   * create a tree of 26 upper-case characters randomly split and join the tree at different
   * locations validate the split and re-joined tree
   */
  @Test
  public void testSplits() {

    tree = SplayTreeWithSize.create();
    for (char c = 'A'; c <= 'Z'; c++) {
      tree.append("" + c);
    }
    System.err.println(tree.printTree());

    for (int splitPoint : shuffledInts(0, tree.size())) {
      SplayTreeWithSize<String> splitter = tree.split(splitPoint);
      System.err.println("split at " + splitPoint);
      System.err.println("newTree size: " + splitter.size());
      System.err.println(splitter.printTree("split off tree"));

      splitter.validate();
      System.err.println("tree size: " + tree.size());
      System.err.println(tree.printTree("tree after split"));
      System.err.println(
          "__________________________________________________________________________");

      tree.validate();

      tree.join(splitter);
      System.err.println(tree.printTree("Joined Tree"));
      tree.validate();
    }
  }

  // Implementing Fisher–Yates shuffle
  static void shuffleArray(int[] ar) {
    Random rnd = ThreadLocalRandom.current();
    for (int i = ar.length - 1; i > 0; i--) {
      int index = rnd.nextInt(i + 1);
      // Simple swap
      int a = ar[index];
      ar[index] = ar[i];
      ar[i] = a;
    }
  }

  static int[] shuffledInts(int from, int to) {
    int[] array = IntStream.rangeClosed(from, to).toArray();
    shuffleArray(array);
    return array;
  }
}
