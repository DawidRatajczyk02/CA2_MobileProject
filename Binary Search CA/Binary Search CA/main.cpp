#include "BinarySearchTree.h"
#include <iostream>

int main() {
    BinarySearchTree bst;

    bst.insert(10);
    bst.insert(5);
    bst.insert(15);
    bst.insert(3);
    bst.insert(7);

    std::cout << "Preorder traversal: ";
    bst.preorder();

    bst.remove(5);

    std::cout << "After removing 5: ";
    bst.preorder();

    return 0;
}