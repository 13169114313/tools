package com.goldmsg.gmvcs.syn.common;

/**
 * @author avx
 * 复选框类
 */

public class Checkbox {

  private String id;
  private String name;
  /**默认未选择*/
  private boolean check=false;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isCheck() {
    return check;
  }

  public void setCheck(boolean check) {
    this.check = check;
  }
}
