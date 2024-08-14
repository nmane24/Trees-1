// {Approach 1}
// Time Complexity : O(N) ...... N is toltal number of elements in tree
// Space Complexity : O(N)  .. we are maintaing a list that will be storing all the elements in ascending order
//
// {Approach 2, 3, 4}
// Time Complexity : O(N) .........N is total number of elements in tree
// Space Complexity : O(1); if we consider stack used for recursion --> O(hight of tree)
//
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this :

/*
Leetcode : https://leetcode.com/problems/validate-binary-search-tree/description/

Given the root of a binary tree, determine if it is a valid binary search tree (BST).
A valid BST is defined as follows:
The left subtree of a node contains only nodes with keys less than the node's key.
The right subtree of a node contains only nodes with keys greater than the node's key.
Both the left and right subtrees must also be binary search tree

Example
Input: root = [2,1,3]
Output: true

Input: root = [5,1,4,null,null,3,6]
Output: false
Explanation: The root node's value is 5 but its right child's value is 4.

/*
Approach 1 : Using extra space  using List
// Time Complexity : O(N) ...... N is total number of elements in tree
// Space Complexity : O(N)  .. we are maintaing a list that will be storing all the elements in ascending order

Preorder - root -> left -> right OR   Reverse Pre-order root -> left -> right
Inorder Traversal - left -> root -> right    OR   REverse Inorder right -> root -> left
Postorder - left -> right -> root    OR    right->left->root

If we do inorder traversal on BST it would give us the sorted order, or ascending order of elements, Simlarly Reverse Inorder 
would give descending order of elements.

Tree is traversed in inOrder way and each visited node is stored in an array.
As per binary search tree properties, this array should be sorted in asceding order.
If any element is less than previous element in this array, that means this is not a binary
search tree. 

WE store all the elements after traversing in inorder manner in a list. Now that list will O(N) take extra space 
*/

public class validate_binary_search_tree {
    List<Integer> listNode = new ArrayList<>();
    public boolean isValidBST(TreeNode root) {
        if(root == null) return false;
        inOrderTraversal(root);
        if(listNode.size() == 1) return true;
        for(int i = 1; i<listNode.size(); i++){
            if(listNode.get(i) <= listNode.get(i-1)) return false;
        }
        return true;
    }

    private void inOrderTraversal(TreeNode root){
        //base
        if(root == null) return;
        inOrderTraversal(root.left);
        listNode.add(root.val);
        inOrderTraversal(root.right);
        
    }
}


/*
Approach 2 : Without extra space /constant space
// Time Complexity : O(N) ...... N is total number of elements in tree
// Space Complexity : O(1)


prev pointer is used to keep track of previously visited node.
comapring root-> val with prev->val is nothing but comparing listNode[i] with listNode[i-1] in 
approach 1.
 We are using a boolean flag here 

 Also if we take prev as local variable, meaning itt is passed as a parameter, then we will not be able to find breach in BST
 if it exist; but if we take as global variable we will able to locate the breach. If parameter below had been a pointer it would have work,
 pointer will always point to a same memory location which is equivalent to global variable in Java.

*/
public class validate_binary_search_tree {
    boolean flag;
    TreeNode prev;

    public boolean isValidBST(TreeNode root) {
        this.flag = true;
        inOrderTraversal(root);
        return flag;
    }

    private void inOrderTraversal(TreeNode root){
        //base
        if(root == null) return;

        // root.left
        inOrderTraversal(root.left); //When functions call are returned on this line, stack.pop happens

        // breach
        if(prev != null && prev.val >= root.val){
            flag = false;
        }
        // visit root
        prev = root;
        
        // visit right node
        inOrderTraversal(root.right);
    }

    // restrict on printing all nodes  - MEthod 1 ( known as conditional recursion)

    private void inOrderTraversal(TreeNode root){
        //base
        if(root == null) return;

        // root.left
        inOrderTraversal(root.left); //When functions call are returned on this line, stack.pop happens

        // breach
        if(prev != null && prev.val >= root.val){
            flag = false;
        }
        // visit root
        prev = root;
        
        // visit right node
        if(flag)
            inOrderTraversal(root.right);
    }


     // restrict on printing all nodes  - MEthod2 ( known as conditional recursion)
    private void inOrderTraversal(TreeNode root){
        //base
        if(root == null || !flag) return;

        // root.left
        inOrderTraversal(root.left); //When functions call are returned on this line, stack.pop happens

        // breach
        if(prev != null && prev.val >= root.val){
            flag = false;
        }
        // visit root
        prev = root;
        
        // visit right node
        inOrderTraversal(root.right);
    }
}

/*
Approach 3 : Without extra space /constant space
// Time Complexity : O(N) ...... N is total number of elements in tree
// Space Complexity : O(1)

This approach is similar to approach 2.
But instead of using extra space 'flag', helper function itself is returning
boolean value.
*/

public class validate_binary_search_tree {
    TreeNode prev;
    public boolean isValidBST(TreeNode root) {
        if(root == null) return false;
        return inOrderTraversal(root);
    }

    // without chcking and printing all the nodes
    private boolean inOrderTraversal(TreeNode root){
        if(root == null) return true;

        boolean left = inOrderTraversal(root.left);
        //breach
        if(prev!=null && prev.val >= root.val){
            return false;
        }
        prev = root;

        boolean right = inOrderTraversal(root.right);
        return left && right;
    }


    // checking and  restricting to printing all the nodes
    private boolean inOrderTraversal(TreeNode root){
        if(root == null) return true;

        boolean left = inOrderTraversal(root.left);
        //breach
        if(prev!=null && prev.val >= root.val){
            return false;
        }
        prev = root;
        if(!left) return false; // this condition is to put a limit on priting all the nodes , we do not make the right call only
        boolean right = inOrderTraversal(root.right);
        return left && right;
    }

    // other way of writing above
    // checking and  restricting to printing all the nodes
    private boolean inOrderTraversal(TreeNode root){
        if(root == null) return true;
        // we are checking here only and limiting the number of nodes going inside the stack and so that right calls will not be made
        if(inOrderTraversal(root.left) == false) return false;
        //breach
        if(prev!=null && prev.val >= root.val){
            return false;
        }
        prev = root;
        boolean right = inOrderTraversal(root.right);
        return right;
    }

}


/*
 * Aprroach 4 : Limits method
 * Possible range of the number we can have for integer of the root vale can be -infinty to + infinity
 * 
 * min and max limit is passed while traversing the tree and at each node
it is checked if node value is less then min value and more than max value.

As we keep going left keep the min same (-infinity) and update the max to root value
If we are going right keep your max same (+infinity) and update the min to root value.

Refer video - 

This approach does not matter which traversal we use. 

Time Complexity :   O(N) ... N of total number of nodes in the binary tree 
Space Complexity:   O(h) .. h is the height of the stack
 */


 public class validate_binary_search_tree {
    
    public boolean isValidBST(TreeNode root) {
        this.flag = true;
        helper(root, null, null);
        return flag;
        
    }

    private void helper(TreeNode root, Integer min, Integer max){
        // base condition

        // if(root == null) return; 
        //if we only write above statement, then all the nodes will get printed hence we add flag check like below which will
        // restrict printing all nodes when there is a breach
        if(root == null || !flag) return;

        //breach conditions ( here the order of breach conditions do no matter as we are checking for each node min and max and not with prev node.
        // so it can also be put inorder , postorder , or preorder way. Currently it is preorder way)
        // breach conditions

        if(min != null && root.val <= min){
            flag = false;
            return; 
        }

        if(max != null && root.val >= max){
            flag = false;
            return; // even if we do  put this return, it will print all the nodes even if there is a breach. To avoid that we add !flag at base condition
        }

        //logic
        //left
        helper(root.left, min, root.val);

        //right
        helper(root.right, root.val, max);
    }

}


/*
 * No difference than approach 4 but other way of restricing printing all the nodes from tree, 
 * by putting a check of flag on the traversal call
 *  TC : O(n)
 * SC  : O(h)
 */

 public class validate_binary_search_tree {
    
    public boolean isValidBST(TreeNode root) {
        this.flag = true;
        helper(root, null, null);
        return flag;
        
    }

    private void helper(TreeNode root, Integer min, Integer max){
        // base condition

         if(root == null) return; 

        //breach conditions ( here the order of breach conditions do no matter as we are checking for each node min and max and not with prev node.
        // so it can also be put inorder , postorder , or preorder way. Currently it is preorder way)
        // breach conditions

        if(min != null && root.val <= min){
            flag = false;
            return; 
        }

        if(max != null && root.val >= max){
            flag = false;
            return; // even if we do  put this return, it will print all the nodes even if there is a breach. To avoid that we add !flag at base condition
        }

        // before traversals flag checks done , which will restrict the printing all of the nodes when the breach condition occurs
        //left
        if(flag)
            helper(root.left, min, root.val);

        //right
        if(flag)
            helper(root.right, root.val, max);
    }

}