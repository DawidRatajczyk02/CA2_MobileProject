#include "BinaryTree.h"

class BinarySearchTree : public BinaryTree {
private:
    TreeNode* insert(TreeNode* node, int value);
    TreeNode* remove(TreeNode* node, int value);;

public:
    BinarySearchTree();
    ~BinarySearchTree();

    void insert(int value);
    void remove(int value);
};