package cn.cncc.caos.external.provider.cloud.model.auth.hwcloud.ops;

public class OpsAuthProps {
    String url;
    String username;
    String userpassword;
    String domainname;

    String projectname;

    public OpsAuthProps() {
    }

    public OpsAuthProps(String url, String username, String userpassword, String domainname, String projectname) {
        this.url = url;
        this.username = username;
        this.userpassword = userpassword;
        this.domainname = domainname;
        this.projectname = projectname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getDomainname() {
        return domainname;
    }

    public void setDomainname(String domainname) {
        this.domainname = domainname;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    @Override
    public String toString() {
        return "OpsAuthProps{" +
                "url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", userpassword='" + userpassword + '\'' +
                ", domainname='" + domainname + '\'' +
                ", projectname='" + projectname + '\'' +
                '}';
    }
}
