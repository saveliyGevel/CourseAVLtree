package com.company;

import static com.company.AVLtreeTest.*;

/**
 * Created by admin on 26.06.17.
 */
public class Main {
    public static void main(String[] args) throws NodeAlreadyExistsException {
        AVLtreeAutoTest test = new AVLtreeAutoTest();
        AVLtree tree = new AVLtree();

        int[] values = {10, 7, 13, 2, 5};
        int[] keys = {1, 3, 5, 6, 2};

        // test insert()
        try {
            print(TESTING_INSERT, true);
            for (int i = 0; i < 5; i++) {
                print(INSERTING + keys[i] + " " + values[i]+ ELLIPSIS, false);
                tree.put(keys[i],values[i]);
            }
        } catch (Exception e) {
            print(e.toString(), true);
        }

        // test find()
        try {
            print(TESTING_FIND, true);
            for (int i = 0; i < 5; i++) {
                print(FINDING + keys[i] + ELLIPSIS, false);
                if (tree.containsKey(keys[i])) {
                    print(SUCCESS, true);
                } else {
                    print(FAILED, true);
                }
            }
        } catch (Exception e) {
            print(e.toString(), true);
        }

        try {
            print(TESTING_DELETE, true);
            for(int i = 0; i < 2;i++){
                print(DELETE + keys[i] + ELLIPSIS, false);
                if(tree.remove(keys[i],values[i])){
                    print(SUCCESS, true);
                } else {
                    print(FAILED, true);
                }
            }
        } catch (Exception e){
            print(e.toString(), true);
        }
    }
}
