package cn.cncc.caos.log.provider;

import cn.cncc.cjdp5.common.core.domain.BaseEntity;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QueryDate extends BaseEntity {
  private String id;
  private String[] gettime;//采集时间
  private String[] puttime;//入库时间
  private String collno;   //采集序号
  private String machine;//服务器名
  private String syscode;//所属系统
  private String appcode;//所属应用
  private String logtype;//日志类型
  private String nodecode;//节点类型
  private String filename;//文件名称
  private String filedir; //文件路径
  private String filenode;//文件节点号
  private String logdata; //日志数据

  private String from;
  private String size;

  private String mintime;
  private String maxtime;

  private String notmachine;//服务器名
  private String notsyscode;//所属系统
  private String notappcode;//所属应用
  private String notfilename;//文件名称
  private String notnodecode;//节点类型

  private String updown;//1上 0下

  public String getNotmachine() {
    if (StringUtils.isNotEmpty(this.machine) && this.machine.trim().startsWith("NOT ")) {
      String substr = this.machine.trim().substring(3, this.machine.trim().length());
      String trim = substr.trim();
      trim = getParam(trim);
      return trim;
    }
    return notmachine;
  }

  public void setNotmachine(String notmachine) {
    this.notmachine = notmachine;
  }

  public String getNotsyscode() {
    if (StringUtils.isNotEmpty(this.syscode) && this.syscode.trim().startsWith("NOT ")) {
      String substr = this.syscode.trim().substring(3, this.syscode.trim().length());
      String trim = substr.trim();
//            String str = "\"";
//            trim = trim.replace(str,"\\\"");
//            str = "[";
//            trim = trim.replace(str,"\\[");
//            str = "]";
//            trim = trim.replace(str,"\\]");
//            str = "{";
//            trim = trim.replace(str,"\\{");
//            str = "}";
//            trim = trim.replace(str,"\\}");
      trim = getParam(trim);
      return trim;
    }
    return notsyscode;
  }

  public void setNotsyscode(String notsyscode) {
    this.notsyscode = notsyscode;
  }

  public String getNotappcode() {
    if (StringUtils.isNotEmpty(this.appcode) && this.appcode.trim().startsWith("NOT ")) {
      String substr = this.appcode.trim().substring(3, this.appcode.trim().length());
      String trim = substr.trim();
//            String str = "\"";
//            trim = trim.replace(str,"\\\"");
//            str = "[";
//            trim = trim.replace(str,"\\[");
//            str = "]";
//            trim = trim.replace(str,"\\]");
//            str = "{";
//            trim = trim.replace(str,"\\{");
//            str = "}";
//            trim = trim.replace(str,"\\}");
      trim = getParam(trim);
      return trim;
    }

    return notappcode;
  }

  public void setNotappcode(String notappcode) {
    this.notappcode = notappcode;
  }

  public String getNotfilename() {
    if (StringUtils.isNotEmpty(this.filename) && this.filename.trim().startsWith("NOT ")) {
      String substr = this.filename.trim().substring(3, this.filename.trim().length());
      String trim = substr.trim();
      trim = getParam(trim);
      return trim;
    }
    return notfilename;
  }

  public void setNotfilename(String notfilename) {
    this.notfilename = notfilename;
  }

  public String getNotnodecode() {
    if (StringUtils.isNotEmpty(this.nodecode) && this.nodecode.trim().startsWith("NOT ")) {
      String substr = this.nodecode.trim().substring(3, this.nodecode.trim().length());
      String trim = substr.trim();
      trim = getParam(trim);
      return trim;
    }
    return notnodecode;
  }

  public void setNotnodecode(String notnodecode) {
    this.notnodecode = notnodecode;
  }

  public String getMintime() {
    if (this.gettime != null) {
      this.mintime = this.gettime[0];
    } else if (this.mintime == null) {
//            Calendar calendar = Calendar.getInstance(); //得到日历
//            calendar.setTime(new Date());//把当前时间赋给日历
//            calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
//            Date dBefore  = calendar.getTime();//得到前一天的时间
//            SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss"); //设置时间格式
//            String defaultStartDate = sdf.format(dBefore);    //格式化前一天
//            this.mintime = defaultStartDate;
      this.mintime = "2024-05-18 00:00:00";
    }
    return mintime;
  }

  public void setMintime(String mintime) {
    this.mintime = mintime;
  }

  public String getMaxtime() {
    if (this.gettime != null) {
      this.maxtime = this.gettime[1];
    } else if (this.maxtime == null) {
      SimpleDateFormat sf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
      this.maxtime = sf.format(new Date());
    }
    return maxtime;
  }

  public void setMaxtime(String maxtime) {
    this.maxtime = maxtime;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String[] getGettime() {
    return gettime;
  }

  public void setGettime(String[] gettime) {
    this.gettime = gettime;
  }

  public String[] getPuttime() {
    return puttime;
  }

  public void setPuttime(String[] puttime) {
    this.puttime = puttime;
  }

  public String getCollno() {
    return collno;
  }

  public void setCollno(String collno) {
    this.collno = collno;
  }

  public String getMachine() {
    if (StringUtils.isNotEmpty(this.machine) && this.machine.trim().startsWith("NOT ")) {
      return null;
    }
    if (StringUtils.isNotEmpty(this.machine)) {
      this.machine = getParam(this.machine);
    }
    return machine;
  }

  public void setMachine(String machine) {
    this.machine = machine;
  }

  public String getSyscode() {
    if (StringUtils.isNotEmpty(this.syscode) && this.syscode.trim().startsWith("NOT ")) {
      return null;
    }
//        if(StringUtils.isNotEmpty(this.syscode)){
//            String str = "\"";
//            this.syscode = syscode.replace(str,"\\\"");
//            str = "[";
//            this.syscode = syscode.replace(str,"\\[");
//            str = "]";
//            this.syscode = syscode.replace(str,"\\]");
//            str = "{";
//            this.syscode = syscode.replace(str,"\\{");
//            str = "}";
//            this.syscode = syscode.replace(str,"\\}");
//        }
    if (StringUtils.isNotEmpty(this.syscode)) {
      this.syscode = getParam(this.syscode);
    }
    return syscode;
  }

  public void setSyscode(String syscode) {
    this.syscode = syscode;
  }

  public String getAppcode() {
    if (StringUtils.isNotEmpty(this.appcode) && this.appcode.trim().startsWith("NOT ")) {
      return null;
    }
//        if(StringUtils.isNotEmpty(this.appcode)){
//            String str = "\"";
//            this.appcode = appcode.replace(str,"\\\"");
//            str = "[";
//            this.appcode = appcode.replace(str,"\\[");
//            str = "]";
//            this.appcode = appcode.replace(str,"\\]");
//            str = "{";
//            this.appcode = appcode.replace(str,"\\{");
//            str = "}";
//            this.appcode = appcode.replace(str,"\\}");
//        }
    if (StringUtils.isNotEmpty(this.appcode)) {
      this.appcode = getParam(this.appcode);
    }
    return appcode;
  }

  public void setAppcode(String appcode) {
    this.appcode = appcode;
  }

  public String getLogtype() {
    return logtype;
  }

  public void setLogtype(String logtype) {
    this.logtype = logtype;
  }

  public String getNodecode() {
    if (StringUtils.isNotEmpty(this.nodecode) && this.nodecode.trim().startsWith("NOT ")) {
      return null;
    }

//        if(StringUtils.isNotEmpty(this.nodecode)){
//            String str = "\"";
//            this.nodecode = nodecode.replace(str,"\\\"");
//            str = "[";
//            this.nodecode = nodecode.replace(str,"\\[");
//            str = "]";
//            this.nodecode = nodecode.replace(str,"\\]");
//            str = "{";
//            this.nodecode = nodecode.replace(str,"\\{");
//            str = "}";
//            this.nodecode = nodecode.replace(str,"\\}");
//        }
    if (StringUtils.isNotEmpty(this.nodecode)) {
      this.nodecode = getParam(this.nodecode);
    }

    return nodecode;
  }

  public void setNodecode(String nodecode) {
    this.nodecode = nodecode;
  }

  public String getFilename() {
    if (StringUtils.isNotEmpty(this.filename) && this.filename.trim().startsWith("NOT ")) {
      return null;
    }
//        if(StringUtils.isNotEmpty(this.filename)){
//            String str = "\"";
//            this.filename = filename.replace(str,"\\\"");
//            str = "[";
//            this.filename = filename.replace(str,"\\[");
//            str = "]";
//            this.filename = filename.replace(str,"\\]");
//            str = "{";
//            this.filename = filename.replace(str,"\\{");
//            str = "}";
//            this.filename = filename.replace(str,"\\}");
//        }
    if (StringUtils.isNotEmpty(this.filename)) {
      this.filename = getParam(this.filename);
    }
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public String getFiledir() {
    return filedir;
  }

  public void setFiledir(String filedir) {
    this.filedir = filedir;
  }

  public String getFilenode() {
    return filenode;
  }

  public void setFilenode(String filenode) {
    this.filenode = filenode;
  }

  public String getLogdata() {
    //正则表达式中 15 个有特殊意义的元字符
    //(    [     {    \    ^    -    $     **    }    ]    )    ?    *    +    .

//        if(this.logdata != null){
//            String str = "\"";
//            this.logdata = logdata.replace(str,"\\\"");
//            str = "[";
//            this.logdata = logdata.replace(str,"\\[");
//            str = "]";
//            this.logdata = logdata.replace(str,"\\]");
//            str = "{";
//            this.logdata = logdata.replace(str,"\\{");
//            str = "}";
//            this.logdata = logdata.replace(str,"\\}");
////            str = ":";
////            this.logdata = logdata.replace(str,"\\:");
////            str = "/";
////            this.logdata = logdata.replace(str,"\\/");
////            str = "\\";
////            this.logdata = logdata.replace(str,"\\\\\\\\");
//        }
    if (StringUtils.isNotEmpty(this.logdata)) {
      this.logdata = getParam(this.logdata);
    }

    return logdata;
  }

  public void setLogdata(String logdata) {
    this.logdata = logdata;
  }

  public String getUpdown() {
    return updown;
  }

  public void setUpdown(String updown) {
    this.updown = updown;
  }


  public String getParam(String param) {
    if (StringUtils.isNotEmpty(param)) {
      String str = "\"";
      param = param.replace(str, "\\\"");
      str = "[";
      param = param.replace(str, "\\\\[");
      str = "]";
      param = param.replace(str, "\\\\]");
      str = "{";
      param = param.replace(str, "\\\\{");
      str = "}";
      param = param.replace(str, "\\\\}");
      str = ":";
      param = param.replace(str, "\\\\:");
      return param;
    }
    return null;
  }


}
