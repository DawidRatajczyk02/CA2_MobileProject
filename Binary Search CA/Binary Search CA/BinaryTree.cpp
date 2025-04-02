#include "BinaryTree.h"
#include <iostream>

TreeNode::TreeNode(int value) : data(value), left(nullptr), right(nullptr) {}

TreeNode::~TreeNode() {
    // Destruction handled by BinaryTree
}

BinaryTree::BinaryTree() : root(nullptr) {}

BinaryTree::~BinaryTree() {
    destroy(root);
}

void BinaryTree::destroy(TreeNode* node) {
    if (node) {
        destroy(node->left);
        destroy(node->right);
        delete node;
    }
}

void BinaryTree::preorder(TreeNode* node) {
    if (node) {
        std::cout << node->data << " ";
        preorder(node->left);
        preorder(node->right);
    }
}

void BinaryTree::preorder() {
    preorder(root);
    std::cout << std::endl;
}