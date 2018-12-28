package main;

import java.net.InetAddress;

public class User {

    public String group;
    public String member;
    public InetAddress address;
    public long latestOnlineTime;
    /**
     * 所有的参数都不要传入空值 像null和""
     * 否则用equals()判断相同的用户时会出错
     * @param address 该用户的ip地址
     * @param group 用户组名
     * @param member 用户名
     */
    public User(InetAddress address, String group, String member) {
        this.address = address;
        this.group = group;
        this.member = member;
        latestOnlineTime = 0;
    }

    /**
     * 只要 地址 和 用户名 组名相同则认为是同一个用户
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!group.equals(user.group)) return false;
        if (!member.equals(user.member)) return false;
        return address.equals(user.address);
    }

    @Override
    public int hashCode() {
        int result = group.hashCode();
        result = 31 * result + member.hashCode();
        result = 31 * result + address.hashCode();
        return result;
    }
}
