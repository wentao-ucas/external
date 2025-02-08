package cn.cncc.caos.uaa.model;


import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;

public class UserHolder {

    private static ThreadLocal<BaseUser> TL = new ThreadLocal<>();

    /**
     * 存储用户 到当前线程
     *
     * @param user
     */
    public static void setUser(BaseUser user) {
        TL.set(user);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static BaseUser getUser() {
        return TL.get();
    }

    /**
     * 删除ThreadLocal中线程用户信息
     */
    public static void remove() {
        TL.remove();
    }
}
