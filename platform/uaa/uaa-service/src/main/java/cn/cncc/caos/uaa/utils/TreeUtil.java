package cn.cncc.caos.uaa.utils;

import cn.cncc.caos.common.core.exception.BapLogicException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
public class TreeUtil {
  public static <T> Tree<T> build(List<Tree<T>> nodes) {

    if (nodes == null) {
      return null;
    }
    List<Tree<T>> topNodes = new ArrayList<Tree<T>>();

//        EXTERNAL:
    for (Tree<T> children : nodes) {

      String pid = children.getParentId();
      if (pid == null || "".equals(pid) || "-1".equals(pid)) {
        topNodes.add(children);

        continue;
      }

      for (Tree<T> parent : nodes) {
        String id = parent.getId();
        if (id != null && id.equals(pid)) {
          parent.getChildren().add(children);
          children.setParent(true);
          children.setParentTree(parent);
          parent.setChildren(true);

//                    continue EXTERNAL;
        }
      }

    }

    Tree<T> root = new Tree<T>();
    if (topNodes.size() == 1) {
      root = topNodes.get(0);
    } else {
      // root.setId("-1");
      root.setParentId("");
      root.setParent(false);
      root.setChildren(true);
      root.setChecked(true);
      root.setChildren(topNodes);
      root.setText("顶级节点");

    }

    return root;
  }

  // hasOwnText是否含有本身text
  public static <T> Map<String, String> getAllParentText(List<Tree<T>> nodes, int hasOwnText) {

    Map<String, String> map = new HashMap<String, String>();
    for (Tree<T> children : nodes) {
      Tree<T> tmpParent = children.getParentTree();
      StringBuilder tmpParentString = new StringBuilder();
      if (hasOwnText == 1) {
        tmpParentString = new StringBuilder(children.getText());
      }

//      log.info(String.format("start id=%s, pid=%s, name=%s ,text=%s", children.getId(), children.getParentId(), children.getName(), children.getText()));
      int i = 0;
      while (tmpParent != null) {
        if (tmpParent.getParentId() != null && !tmpParent.getParentId().equals("-1") && !tmpParent.getParentId().equals(""))
          tmpParentString.insert(0, tmpParent.getText() + ">");
        tmpParent = tmpParent.getParentTree();
        if (i++ > 1000) {
          throw new BapLogicException("数据错误，导致死循环！");
        }
      }
//      log.info(String.format("end id = %s, pid = %s, name = %s, text = %s, str = %s", children.getId(), children.getParentId(), children.getName(), children.getText(), tmpParentString));

      map.put(children.getId(), tmpParentString.toString());
    }
    return map;
  }
}


