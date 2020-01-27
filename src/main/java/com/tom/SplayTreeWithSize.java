package com.tom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the SplayTree from the wiki page C++ code: https://en.wikipedia.org/wiki/Splay_tree I've
 * added a size property to the node and the rotate methods keep it up-to-date. @TODO: add unit
 * tests for split and join
 *
 * @param <T> the type of Comparable to store in the tree
 */
public class SplayTreeWithSize<T extends Comparable<T>> {

  private static final Logger log = LoggerFactory.getLogger(SplayTreeWithSize.class);

  static <T extends Comparable<T>> int nodeSize(Node<T> node) {
    return node == null ? 0 : node.size;
  }

  public static class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
    T key;
    Node<T> parent;
    Node<T> left;
    Node<T> right;
    int size;

    public Node(T key) {
      this.key = key;
    }

    @Override
    public int compareTo(Node<T> other) {
      return key.compareTo(other.key);
    }

    public int size() {
      return this.size;
    }

    int count() {
      int leftCount = left != null ? left.count() : 0;
      int rightCount = right != null ? right.count() : 0;
      int count = 1 + leftCount + rightCount;
      return count;
    }

    /** validate a node */
    public void validate() {
      if (this == left) {
        throw new RuntimeException("this == left");
      }
      if (this == right) {
        throw new RuntimeException("this == right");
      }
      if (this == this.parent) {
        throw new RuntimeException("node is its own parent");
      }
      if (left != null && left == right) {
        throw new RuntimeException("children match");
      }
      if (parent != null) {
        if (parent.left != this && parent.right != this) {
          throw new RuntimeException("parent child mismatch");
        }
      }
    }
  }

  Node<T> root;

  public static <T extends Comparable<T>> SplayTreeWithSize<T> create() {
    return new SplayTreeWithSize<>();
  }

  public static <T extends Comparable<T>> SplayTreeWithSize<T> create(Node<T> root) {
    return new SplayTreeWithSize<>(root);
  }

  private SplayTreeWithSize() {}

  private SplayTreeWithSize(Node<T> root) {
    this.root = root;
  }

  void leftRotate(Node<T> x) {
    Node<T> y = x.right;
    if (y != null) {
      x.right = y.left;
      if (y.left != null) y.left.parent = x;
      y.parent = x.parent;
    }

    if (x.parent == null) root = y;
    else if (x == x.parent.left) x.parent.left = y;
    else x.parent.right = y;
    if (y != null) y.left = x;

    x.size = nodeSize(x.left) + nodeSize(x.right) + 1;

    x.parent = y;
  }

  void rightRotate(Node<T> x) {
    Node<T> y = x.left;
    if (y != null) {
      x.left = y.right;
      if (y.right != null) y.right.parent = x;
      y.parent = x.parent;
    }
    if (null == x.parent) root = y;
    else if (x == x.parent.left) x.parent.left = y;
    else x.parent.right = y;
    if (y != null) y.right = x;

    x.size = nodeSize(x.left) + nodeSize(x.right) + 1;

    x.parent = y;
  }

  public void splay(T element) {
    Node<T> node = find(element);
    if (node != null) {
      splay(node);
    }
  }

  public void splay(Node<T> x) {
    int rootSize = nodeSize(root);
    int leftSize = 0;
    int rightSize = 0;

    while (x.parent != null) {
      if (null == x.parent.parent) {
        if (x.parent.left == x) rightRotate(x.parent);
        else leftRotate(x.parent);
      } else if (x.parent.left == x && x.parent.parent.left == x.parent) {
        rightRotate(x.parent.parent);
        rightRotate(x.parent);
      } else if (x.parent.right == x && x.parent.parent.right == x.parent) {
        leftRotate(x.parent.parent);
        leftRotate(x.parent);
      } else if (x.parent.left == x && x.parent.parent.right == x.parent) {
        rightRotate(x.parent);
        leftRotate(x.parent);
      } else {
        leftRotate(x.parent);
        rightRotate(x.parent);
      }
    }

    leftSize += nodeSize(root.left); /* Now l_size and r_size are the sizes of */
    rightSize += nodeSize(root.right); /* the left and right trees we just built.*/
    root.size = leftSize + rightSize + 1;
  }

  static <T extends Comparable<T>> Node<T> p(Node<T> node) {
    return node.parent;
  }

  static <T extends Comparable<T>> int size(Node<T> node) {
    return node != null ? node.size() : 0;
  }

  static <T extends Comparable<T>> Node<T> l(Node<T> node) {
    return node.left;
  }

  static <T extends Comparable<T>> Node<T> r(Node<T> node) {
    return node.right;
  }

  public int pos(Node<T> node) {
    if (node == root) {
      return size(l(node));
    } else if (r(p(node)) == node) { // node is a right child
      return pos(p(node)) + size(l(node)) + 1;
    } else if (l(p(node)) == node) { // node is a left child
      return pos(p(node)) - size(r(node)) - 1;
    } else {
      return -1;
    }
  }

  void replace(Node<T> u, Node<T> v) {
    if (null == u.parent) root = v;
    else if (u == u.parent.left) u.parent.left = v;
    else u.parent.right = v;
    if (v != null) v.parent = u.parent;
  }

  Node<T> subtree_minimum(Node<T> u) {
    while (u.left != null) u = u.left;
    return u;
  }

  Node<T> subtree_maximum(Node<T> u) {
    while (u.right != null) u = u.right;
    return u;
  }

  public Node<T> max() {
    return subtree_maximum(root);
  }

  public Node<T> min() {
    return subtree_minimum(root);
  }

  public void insert(T key) {
    Node<T> z = root;
    Node<T> p = null;

    while (z != null) {
      p = z;
      if (comp(z.key, key)) z = z.right;
      else z = z.left;
    }

    z = new Node<>(key);
    z.size = 1;
    z.parent = p;

    if (z.parent != null) {
      z.parent.size += z.size;
    }
    if (null == p) {
      root = z;
      //      root.size += z.size;
    } else if (comp(p.key, z.key)) {
      p.right = z;
      //      p.size += z.size;
    } else {
      p.left = z;
      //      p.size += z.size;
    }

    splay(z);
  }

  public void append(T key) {
    Node<T> z = new Node<>(key);
    z.size = 1;
    if (root == null) {
      root = z;
      return;
    }

    Node<T> max = max();
    splay(max);

    System.err.println(printTree("Appending " + key));

    max.right = z;
    max.size += z.size;
    z.parent = max;
    System.err.println(printTree("Appended " + key));
  }

  public static <T extends Comparable<T>> void join(Pair<SplayTreeWithSize<T>> trees) {
    // find my largest item
    // assert that the max of left is smaller than the min of right
    if (!comp(trees.first.max(), trees.second.min())) {
      throw new IllegalArgumentException();
    }
    Node<T> largest = trees.first.max();
    trees.first.splay(largest);
    trees.first.root.right = trees.second.root;
  }

  public void join(SplayTreeWithSize<T> joiner) {
    Node<T> largest = max();
    splay(largest);
    root.right = joiner.root;
    if (joiner.root != null) {
      joiner.root.parent = root;
      root.size += joiner.root.size;
    } else {
      System.err.println("joiner root is null");
    }
  }

  public SplayTreeWithSize<T> split(T key) {
    // split off the right side of key
    Node<T> node = find(key);
    SplayTreeWithSize<T> splitter = SplayTreeWithSize.create(node.right);
    node.right = null;
    return splitter;
  }

  public Node<T> find(int k) {
    return find(root, k);
  }

  Node<T> find(Node<T> node, int k) {
    if (node == null) return null;
    int pos = pos(node);

    if (pos == k) {
      return node;
    }
    if (pos < k) {
      return find(node.right, k);
    } else {
      return find(node.left, k);
    }
  }

  public SplayTreeWithSize<T> split(int position) {
    Node<T> found = find(position);
    if (found != null) {
      splay(found);
      // split off the right side of key
      if (found.right != null) {
        found.right.parent = null;
        found.size -= found.right.size;
      }
      SplayTreeWithSize<T> splitter = SplayTreeWithSize.create(found.right);
      found.right = null;

      validate();
      return splitter;
    }
    return SplayTreeWithSize.create(); // return empty 'right' tree and leave tree alone
  }

  public static <T extends Comparable<T>> Pair<SplayTreeWithSize<T>> split(
      SplayTreeWithSize<T> tree, T key) {
    // assume we find key
    // assume we find key
    Node<T> node = tree.find(key);
    tree.splay(node);
    node.left.parent = null;
    node.right.parent = null;
    return Pair.of(SplayTreeWithSize.create(node.left), SplayTreeWithSize.create(node.right));
  }

  public Node<T> find(T key) {
    Node<T> z = root;
    while (z != null) {
      if (comp(z.key, key)) z = z.right;
      else if (comp(key, z.key)) z = z.left;
      else return z;
    }
    return null;
  }

  public Node<T> findSplay(T key) {
    Node<T> z = root;
    while (z != null) {
      if (comp(z.key, key)) z = z.right;
      else if (comp(key, z.key)) z = z.left;
      else {
        splay(z);
        return z;
      }
    }
    return null;
  }

  public void erase(T key) {
    Node<T> z = find(key);
    if (null == z) return;

    splay(z);

    if (null == z.left) replace(z, z.right);
    else if (null == z.right) replace(z, z.left);
    else {
      Node<T> y = subtree_minimum(z.right);
      if (y.parent != z) {
        replace(y, y.right);
        y.right = z.right;
        y.right.parent = y;
      }
      replace(z, y);
      y.left = z.left;
      y.left.parent = y;
    }
  }

  public int size() {
    return root != null ? root.size : 0;
  }

  public int height() {
    return height(root);
  }

  public static <T extends Comparable<T>> int height(Node<T> node) {
    return node != null ? 1 + Math.max(height(node.left), height(node.right)) : 0;
  }

  static <T extends Comparable> boolean comp(T one, T two) {
    return one.compareTo(two) < 0;
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
    if (root != null) {
      if (root.parent != null) {
        throw new RuntimeException("root parent is not null");
      }
      root.validate();
      validateChild(root.left);
      validateChild(root.right);
    }
  }

  /**
   * validate a node
   *
   * @param node
   */
  private void validateChild(Node<T> node) {
    if (node == null) return;
    node.validate();
    if (node.parent == null) {
      throw new RuntimeException("child has null parent");
    }
    if (node.size != node.count()) {
      throw new RuntimeException("size does not match count");
    }
    validateChild(node.left);
    validateChild(node.right);
  }
}
