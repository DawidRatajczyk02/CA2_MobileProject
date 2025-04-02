class TreeNode {
public:
    int data;
    TreeNode* left;
    TreeNode* right;

    TreeNode(int value);
    ~TreeNode();
};

class BinaryTree {
protected:
    TreeNode* root;

    void preorder(TreeNode* node);
    void destroy(TreeNode* node);

public:
    BinaryTree();
    virtual ~BinaryTree();

    void preorder();
};