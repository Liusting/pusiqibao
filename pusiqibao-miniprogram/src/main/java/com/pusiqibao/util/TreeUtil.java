package com.pusiqibao.util;

import com.pusiqibao.entity.SpCategory;

import java.util.ArrayList;
import java.util.List;

public class TreeUtil {
    public static List<SpCategory> listToTree(List<SpCategory> list) {
        //用递归找子。
        List<SpCategory> treeList = new ArrayList<SpCategory>();
        for (SpCategory tree : list) {
            //根目录的parentId为0
            if (tree.getParentId() == 0) {
                treeList.add(findChildren(tree, list));
            }
        }
        return treeList;
    }

    private static SpCategory findChildren(SpCategory tree, List<SpCategory> list) {
        for (SpCategory node : list) {
            if (node.getParentId().longValue() == tree.getId().longValue()) {
                if (tree.getChildren() == null) {
                    tree.setChildren(new ArrayList<SpCategory>());
                }
                tree.getChildren().add(findChildren(node, list));
            }
        }
        return tree;
    }

}
