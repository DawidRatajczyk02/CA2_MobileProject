#include "BinarySearchTree.h"

BinarySearchTree::BinarySearchTree() {}

BinarySearchTree::~BinarySearchTree() {}

TreeNode* BinarySearchTree::insert(TreeNode* node, int value) {
    if (!node)
        return new TreeNode(value);
    if (value < node->data)
        node->left = insert(node->left, value);
    else if (value > node->data)
        node->right = insert(node->right, value);
    return node;
}

TreeNode* BinarySearchTree::remove(TreeNode* node, int value) {
    if (!node)
        return nullptr;
    if (value < node->data) {
        node->left = remove(node->left, value);
    }
    else if (value > node->data) {
        node->right = remove(node->right, value);
    }
    else {
        if (!node->left) {
            TreeNode* temp = node->right;
            delete node;
            return temp;
        }
        else if (!node->right) {
            TreeNode* temp = node->left;
            delete node;
            return temp;
        }
        else {
            TreeNode* temp = node->right;
            while (temp && temp->left)
                temp = temp->left;

            node->data = temp->data;
            node->right = remove(node->right, temp->data);
        }
    }
    return node;
}

void BinarySearchTree::insert(int value) {
    root = insert(root, value);
}

void BinarySearchTree::remove(int value) {
    root = remove(root, value);
}