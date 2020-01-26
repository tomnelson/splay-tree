package com.tom;

/**
 * Implements a top-down splay tree. Available at http://www.link.cs.cmu.edu/splay/ Author: Danny
 * Sleator <sleator@cs.cmu.edu> This code is in the public domain.
 *
 * <p>Tom added the size attribute based on Sleator's C version of the same top down splay tree
 */
public class TopDownSplayTree<T extends Comparable<T>> {

  public static class Node<T extends Comparable<T>> {
    public Node(T theKey) {
      key = theKey;
      left = right = null;
    }

    T key; // The data in the node
    Node<T> left; // Left child
    Node<T> right; // Right child
    int size;

    public int size() {
      return size;
    }

    public void validate() {
      if (this == left) {
        throw new RuntimeException("this == left");
      }
      if (left != null && left == right) {
        throw new RuntimeException("children match");
      }
    }
  }

  static <T extends Comparable<T>> int nodeSize(Node<T> node) {
    return node == null ? 0 : node.size;
  }

  public static <T extends Comparable<T>> TopDownSplayTree<T> create() {
    return new TopDownSplayTree<>();
  }

  public static <T extends Comparable<T>> TopDownSplayTree<T> create(
      TopDownSplayTree.Node<T> root) {
    return new TopDownSplayTree<>(root);
  }

  public TopDownSplayTree(Node<T> root) {
    this.root = root;
  }

  public TopDownSplayTree(T root) {
    this.root = new Node<>(root);
  }

  private Node<T> root;

  public TopDownSplayTree() {
    root = null;
  }

  /** Insert into the tree. */
  public void insert(T key) {
    Node<T> n;
    int c;
    if (root == null) {
      root = new Node(key);
      root.size = 1 + nodeSize(root.left) + nodeSize(root.right);
      return;
    }
    splay(key);
    if ((c = key.compareTo(root.key)) == 0) {
      //	    throw new DuplicateItemException(x.toString());
      return;
    }
    n = new Node<>(key);
    if (c < 0) {
      n.left = root.left;
      n.right = root;
      root.left = null;
      root.size = 1 + nodeSize(root.right);
    } else {
      n.right = root.right;
      n.left = root;
      root.right = null;
      root.size = 1 + nodeSize(root.left);
    }
    root = n;
    n.size = 1 + nodeSize(n.left) + nodeSize(n.right);
  }

  /** Insert into the tree. */
  public void append(T key) {
    Node<T> n;
    int c;
    if (root == null) {
      root = new Node(key);
      root.size = 1 + nodeSize(root.left) + nodeSize(root.right);
      return;
    }
    T max = findMax();
    splay(max);
    if ((c = key.compareTo(root.key)) == 0) {
      //	    throw new DuplicateItemException(x.toString());
      return;
    }
    n = new Node<>(key);
    if (c < 0) {
      n.left = root.left;
      n.right = root;
      root.left = null;
      root.size = 1 + nodeSize(root.right);
    } else {
      n.right = root.right;
      n.left = root;
      root.right = null;
      root.size = 1 + nodeSize(root.left);
    }
    root = n;
    n.size = 1 + nodeSize(n.left) + nodeSize(n.right);
  }

  /** Remove from the tree. */
  public void remove(T key) {
    Node<T> x = null;
    splay(key);
    if (key.compareTo(root.key) != 0) {
      //            throw new ItemNotFoundException(x.toString());
      return;
    }
    int rootSize = root.size;
    // Now delete the root
    if (root.left == null) {
      root = root.right;
    } else {
      x = root.right;
      root = root.left;
      splay(key);
      root.right = x;
    }
    if (x != null) {
      x.size = rootSize - 1;
    }
  }

  /** Find the smallest item in the tree. */
  public T findMin() {
    Node<T> x = root;
    if (root == null) return null;
    while (x.left != null) x = x.left;
    splay(x.key);
    return x.key;
  }

  /** Find the largest item in the tree. */
  public T findMax() {
    Node<T> x = root;
    if (root == null) return null;
    while (x.right != null) x = x.right;
    splay(x.key);
    return x.key;
  }

  /** Find an item in the tree. */
  public T find(T key) {
    if (root == null) return null;
    splay(key);
    if (root.key.compareTo(key) != 0) return null;
    return root.key;
  }

  /**
   * Test if the tree is logically empty.
   *
   * @return true if empty, false otherwise.
   */
  public boolean isEmpty() {
    return root == null;
  }

  Node<T> find_rank(int r, Node<T> t) {
    /* Returns a pointer to the node in the tree with the given rank.  */
    /* Returns NULL if there is no such node.                          */
    /* Does not change the tree.  To guarantee logarithmic behavior,   */
    /* the node found here should be splayed to the root.              */
    int lsize;
    if ((r < 0) || (r >= nodeSize(t))) return null;
    for (; ; ) {
      lsize = nodeSize(t.left);
      if (r < lsize) {
        t = t.left;
      } else if (r > lsize) {
        r = r - lsize - 1;
        t = t.right;
      } else {
        return t;
      }
    }
  }

  /** this method just illustrates the top-down method of implementing the move-to-root operation */
  private void moveToRoot(T key) {
    Node<T> l, r, t, y;
    l = r = header;
    t = root;
    header.left = header.right = null;
    for (; ; ) {
      if (key.compareTo(t.key) < 0) {
        if (t.left == null) break;
        r.left = t; /* link right */
        r = t;
        t = t.left;
      } else if (key.compareTo(t.key) > 0) {
        if (t.right == null) break;
        l.right = t; /* link left */
        l = t;
        t = t.right;
      } else {
        break;
      }
    }
    l.right = t.left; /* assemble */
    r.left = t.right;
    t.left = header.right;
    t.right = header.left;
    root = t;
  }

  private static Node header = new Node<>(null); // For splay

  /**
   * Internal method to perform a top-down splay.
   *
   * <p>splay(key) does the splay operation on the given key. If key is in the tree, then the
   * BinaryNode containing that key becomes the root. If key is not in the tree, then after the
   * splay, key.root is either the greatest key < key in the tree, or the lest key > key in the
   * tree.
   *
   * <p>This means, among other things, that if you splay with a key that's larger than any in the
   * tree, the rightmost node of the tree becomes the root. This property is used in the delete()
   * method.
   */
  public void splay(T key) {
    Node<T> N = header;
    Node<T> l, r, t, y;
    l = r = (Node<T>) header;
    t = root;
    int rootSize = nodeSize(root);
    int leftSize = 0;
    int rightSize = 0;
    header.left = header.right = null;
    for (; ; ) {
      if (key.compareTo(t.key) < 0) {
        if (t.left == null) break;
        if (key.compareTo(t.left.key) < 0) {
          y = t.left; /* rotate right */
          t.left = y.right;
          y.right = t;
          t.size = nodeSize(t.left) + nodeSize(t.right) + 1;
          t = y;
          if (t.left == null) break;
        }
        r.left = t; /* link right */
        r = t;
        t = t.left;
        rightSize += 1 + nodeSize(r.right);
      } else if (key.compareTo(t.key) > 0) {
        if (t.right == null) break;
        if (key.compareTo(t.right.key) > 0) {
          y = t.right; /* rotate left */
          t.right = y.left;
          y.left = t;
          t.size = nodeSize(t.left) + nodeSize(t.right) + 1;
          t = y;
          if (t.right == null) break;
        }
        l.right = t; /* link left */
        l = t;
        t = t.right;
        leftSize += 1 + nodeSize(l.left);
      } else {
        break;
      }
    }
    leftSize += nodeSize(t.left); /* Now l_size and r_size are the sizes of */
    rightSize += nodeSize(t.right); /* the left and right trees we just built.*/
    t.size = leftSize + rightSize + 1;

    l.right = r.left = null;

    /* The following two loops correct the size fields of the right path  */
    /* from the left child of the root and the right path from the left   */
    /* child of the root.                                                 */
    for (y = header.right; y != null; y = y.right) {
      y.size = leftSize;
      leftSize -= 1 + nodeSize(y.left);
    }
    for (y = header.left; y != null; y = y.left) {
      y.size = rightSize;
      rightSize -= 1 + nodeSize(y.right);
    }
    l.right = t.left; /* assemble */
    r.left = t.right;
    t.left = header.right;
    t.right = header.left;
    root = t;
  }

  public String printTree() {
    return printTree(root, 0);
  }

  public String printTree(Node<T> node, int d) {
    StringBuilder builder = new StringBuilder();
    int i;
    if (node == null) return "";
    builder.append(printTree(node.right, d + 1));
    for (i = 0; i < d; i++) builder.append("  ");
    builder.append(node.key + "(" + node.size + ")\n");
    builder.append(printTree(node.left, d + 1));
    return builder.toString();
  }

  public String printTree(String note) {
    return note + "\n" + printTree(root, 0);
  }

  public void validate() {
    // root parent is null
    root.validate();
    validateChild(root.left);
    validateChild(root.right);
  }

  private void validateChild(Node<T> node) {
    if (node != null) {
      node.validate();
      validateChild(node.left);
      validateChild(node.right);
    }
  }

  // test code stolen from Weiss
  public static void main(String[] args) {
    TopDownSplayTree<Integer> t = new TopDownSplayTree<>();
    final int NUMS = 40000;
    final int GAP = 307;

    System.out.println("Checking... (no bad output means success)");

    for (int i = GAP; i != 0; i = (i + GAP) % NUMS) t.insert(i);
    System.out.println("Inserts complete");

    for (int i = 1; i < NUMS; i += 2) t.remove(i);
    System.out.println("Removes complete");

    if (t.findMin() != 2 || t.findMax() != NUMS - 2)
      System.out.println("FindMin or FindMax error!");

    for (int i = 2; i < NUMS; i += 2)
      if (t.find(i) != i) System.out.println("Error: find fails for " + i);

    for (int i = 1; i < NUMS; i += 2)
      if (t.find(i) != null) System.out.println("Error: Found deleted item " + i);
  }
}
