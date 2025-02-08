package cn.cncc.caos.uaa.utils;


import cn.cncc.caos.common.core.utils.JacksonUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;

@Slf4j
public class Tree<T> {
  /**
   * 节点ID
   */
  private String id;
  /**
   * 显示节点文本
   */
  private String text;
  /**
   * 匹配字段
   */
  private String name;

  /**
   * 节点状态，open closed
   */
  private String state = "open";
  /**
   * 节点是否被选中 true false
   */
  private boolean checked = false;
  /**
   * 节点的子节点
   */
  private List<Tree<T>> children = new ArrayList<Tree<T>>();

  private Map<String, Object> otherField = new HashMap<String, Object>();

  private boolean expand = false;

  private boolean disabled = false;

  /**
   * 父ID
   */
  private String parentId;
  /**
   * 是否有父节点
   */
  private boolean isParent = false;


  /**
   * 是否有子节点
   */
  private Tree<T> parentTree = null;


  private boolean isChildren = false;

  private T model;

  public T getModel() {
    return model;
  }

  public void setModel(T model) {
    this.model = model;
  }


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public boolean isChecked() {
    return checked;
  }

  public void setChecked(boolean checked) {
    this.checked = checked;
  }

  public List<Tree<T>> getChildren() {
    return children;
  }

  public void setChildren(List<Tree<T>> children) {
    this.children = children;
  }

  public boolean isParent() {
    return isParent;
  }

  public void setParent(boolean isParent) {
    this.isParent = isParent;
  }

  public boolean isChildren() {
    return isChildren;
  }

  public void setChildren(boolean isChildren) {
    this.isChildren = isChildren;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public boolean isExpand() {
    return expand;
  }

  public void setExpand(boolean expand) {
    this.expand = expand;
  }

  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }

  public Tree<T> getParentTree() {
    return parentTree;
  }

  public void setParentTree(Tree<T> parentTree) {
    this.parentTree = parentTree;
  }

  public Tree(String id, String text, String state, boolean checked,
              List<Tree<T>> children,
              boolean isParent, boolean isChildren, String parentID) {
    super();
    this.id = id;
    this.text = text;
    this.state = state;
    this.checked = checked;
    this.children = children;
    this.isParent = isParent;
    this.isChildren = isChildren;
    this.parentId = parentID;
  }

  public Map<String, Object> getOtherField() {
    return otherField;
  }

  public void setOtherField(Map<String, Object> otherField) {
    this.otherField = otherField;
  }

  public Tree() {
    super();
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }


  @SneakyThrows
  @Override
  public String toString() {
    Map json = new HashMap();
    json.put("id", this.id);
    json.put("title", this.text);
    json.put("name", this.name);
    json.put("expand", this.expand);
    json.put("selected", false);
    json.put("checked", false);
//        log.info(json.toJSONString());

    for (Map.Entry<String, Object> entry : otherField.entrySet()) {
      String mapKey = entry.getKey();
      Object mapValue = entry.getValue();
      json.put(mapKey, mapValue);
    }

    if (this.model != null)
      json.put("model", EntityUtil.entityToMap(this.model));

    // json.put("parentId",parentId);
    if (children.size() != 0) {
      List ja = new ArrayList();
      for (Tree<T> child : children) {
        try {
          ja.add(JacksonUtil.jsonToObj(child.toString(),Map.class));
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
      json.put("children", ja);
    } else {
      json.remove("children");
    }
    //json.put("children",JSONArray.fromObject(children));
    return JacksonUtil.objToJson(json);
  }

  public List<Tree<T>> buildAllchildrenByDepthFirst() {
    Tree node = this;
    List<Tree<T>> list = new ArrayList<>();
    Stack<Tree<T>> nodeStack = new Stack<Tree<T>>();
    nodeStack.add(node);
    list.add(node);

    while (!nodeStack.isEmpty()) {
      node = nodeStack.pop();
//            log.info(node.toString());
      //获得节点的子节点，对于二叉树就是获得节点的左子结点和右子节点
      List<Tree<T>> children = node.getChildren();
      if (children != null && !children.isEmpty()) {
        for (Tree<T> child : children) {
          nodeStack.push(child);
          list.add(child);
        }
      }
    }
    return list;
  }
}
