// Time Complexity : O(n^2) ......searching for root index everytime in inorder array , 
//   and copy of range copied one by one elements into new list
// Space Complexity : O(n^2) .. total number of nodes : n, for each node new copy will be created n so : n*n

/* Approach 2
 * TC: O(n)
 * SC: O(n) + O(h) .. n is the space for the hashmap and h is the stack space
 * 
 */

// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this :

/*
Leetcode : https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/description/

Given two integer arrays preorder and inorder where preorder is the preorder traversal of 
a binary tree and inorder is the inorder traversal of the same tree, 
construct and return the binary tree.

Class Example : 
PreOrder = [5,4,7,10,9,8,11,20]
InOrder = [10,7,9,4,8,5,11,20]

Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
Output: [3,9,20,null,null,15,7]
Example 2:

Input: preorder = [-1], inorder = [-1]
Output: [-1]


// Time Complexity : O(n^2) ......searching for root index everytime in inorder array , 
//   and copy of range copied one by one elements into new list
// Space Complexity : O(n^2) .. total number of nodes : n, for each node new copy(copy of range function is nothiing
but deep copy/ slice of array) will be created n so : n*n

Code Explaination :  watch for understanding - https://www.youtube.com/watch?v=AnzeyrnWCeY
 We would locate our root in the pre-order tree and then check that root in the inorder tree.
 Once we locate the root in inorder, then whatever is in the left will form the left subtree and right will be right subtree.
 Because inorder we have left , root , right. So left part forms left sub tree and right part becomes right subtree and this way 
 we keep calling our recursive calls
*/

import java.util.Arrays;
import javax.swing.tree.TreeNode;

public class construct_binary_tree {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // base
        if(preorder.length == 0) return null;
        //logic
        int rootVal = preorder[0];
        TreeNode root = new TreeNode(rootVal);
        int idx = -1;        // idx on inorder arr
        // check for the root in inroder and get its index
        for(int i = 0; i<inorder.length; i++){
            if(inorder[i] == rootVal){
                idx = i;
            }
        }

        int [] inLeft = Arrays.copyOfRange(inorder, 0 , idx); // whatever is on the left of the tree of found index of inorder
        int [] inRight = Arrays.copyOfRange(inorder, idx+1, inorder.length); // whatever is on the right of the tree of found index of inorder

        int [] preLeft = Arrays.copyOfRange(preorder, 1, inLeft.length+1); // skipping the root and hence we have id
        int [] preRight = Arrays.copyOfRange(preorder, inLeft.length+1, preorder.length);

        root.left = buildTree(preLeft, inLeft);
        root.right = buildTree(preRight, inRight);
        return  root;
    }
}


/*
 *  Optimizing the above by not trying to search the root Value in inorder array /iterating over array. And space optimization 
 * needed due to copy of Range function, for every call new copy is created.
 * We need to avoid using copyofRange function and not searching for that rootValue everytime in inorder array
 * 
 * Using hashmap of inorder array , for every node we have with the values of indexes. So when we have to look for rootValue = 5 we 
 * wont be iterating over the array, just look into map where is rootValue 5 and get the index
 * 
 * Make Use of pointers and not create a copy everytime.
 * 
 * Start and end pointer on inorder array and one pointer in inorder array
 * 
 * PreOrder = [5,4,7,10,9,8,11,20]
 * Inorder = [10,7,9,4,8,5,11,20]
 * Function call on 5 of pre order -> F(5, start index, end index, rootVal index) = f(5*, 0, 7, 5)
 * Now we have to move left as we are builting towards left. We move our pointer we have on Preorder array. This pointer will be giving 
 * the next root.  As we go towards left the start index will be as it is and end index will be (rootVal index - 1) ; 
 * so now call will be f(4*, 0, 4, 3). We are getting rootVal index from the map.The root has been build; 
 *  Now the index ptr of preorder will move. Our next root is 7 - f(7*, 0, 2, 1). Mobve the index ptr of preorder.
 * f(10*, 0,0,0). Move the ptr. Next call is for 9. So f(9*, 0, -1, 4) . Now as soon as start and end index have crossed each other
 * it means we are done for the left call. WE CANNOT HAVE ANY FURTHER NODES FROM HERE. Then we pop it our from the stack and returning
 * to the place from where the call was made. Now we returned to 10. For 10 we finished the left call, we try to make the right call
 * So when we are going towards right , start = rootVal index +1 and end will remain as it is comapring with f(10*, 0,0,0)
 * 
 * so f(9*, 1, 0 ), but as start and end have crossed each other this will get popped out. For 10 both left and right are done. Even 10 wil 
 * be popped out. Now we are at f(7*, 0, 2, 1) , we made the left call, we are trying to make the right call which is f(9*, 2, 2, 2). And build
 * root 9 at right of 7. Now the index ptr will be increased . The node is we are trying to build is left og 9 so : f(8*, 2, 1, ).. start 
 * and end have crossed each other. Now we will try to do right of 9 : f(8*, 3, 2, ).. again crossed each other . For 9 both left and right are done. 
 * 9 will be popped out. For 7 both left and right are done, 7 will be popped out. we will come to 4
 * 4, left call was done which was f(4*, 0, 4, 3) , we are trying to build for right : f(8*, 4, 4, 4). We will build the node 8 in tree.
 * now we move the index pointer. so f(11*, 4, 3, ).. crossed each other , left of 8 is popped, now build right over the f(8*, 4, 4, 4)
 * f(11* , 5, 4, ).. crossed each other. So it will be popped out. Now 8 also popped out as both left and right doen. We will come to 4 f(4*, 0, 4, 3).
 * , where both left and right are done. So it will be popped out. Now we are at f(5*, 0, 7, 5). For 5 we made the left call now
 * we make the right call. f(11*, 6, 7, 6) amd we will build the node 7. We move the node ptr and it is pointing to 10 
 * The left of 11 is f(20*, 6, 5 , ).. start and end have crossed each other so popped out. Do the right call of 11 : f(20*, 7,7,7).
 * We will build the node 20. For 20 we will get null, null as start and end have crossed each other pop out, 20 call is done.
 * For 11, both left & right call are done it will be popped out. For 5 both left and right call are done, it will be popped out. 
 * At the end stack is empty we have build our tree.
 * 
 * 
 * TC: O(n).. as we are just iterating over the array
 * SC: O(n) + O(h) .. n is the space for the hashmap and h is the stack space
 */


public class construct_binary_tree {
    int idx;
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // base
        if(preorder.length == 0) return null;
        HashMap<Integer, Integer> map = new HashMap<>();

        for(int i = 0;i<inorder.length; i++){
            map.put(inorder[i], i); // put the inorder array root values with their indexes against it
        }

        return helper(map, 0, inorder.length-1, preorder);
    }

    private TreeNode helper( HashMap<Integer, Integer> map, int start, int end, int [] preorder){
        // base
        if(start > end) return null;

        //logic
        int rootVal = preorder[idx]; // get the rootValue
        idx++;                          // increment the node pointer
        int rootIdx = map.get(rootVal); // get the index of root Value
        TreeNode root = new TreeNode(rootVal); // build the root node
        root.left = helper(map, start, rootIdx -1, preorder);
        root.right = helper(map, rootIdx+1, end, preorder);
        return root;

    } 
}