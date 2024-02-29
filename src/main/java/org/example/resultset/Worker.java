package org.example.resultset;

import java.util.Date;

public class Worker {
    private int id;
    private String name;
    private Date birthday;
    private String level;
    private int salary;

    public String getName() { return name; }
    public Date getBirthday() { return birthday; }
    public String getLevel() { return level; }

    public Worker(int id, String name, Date birthday, String level, int salary) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.level = level;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", level=" + level +
                ", salary=" + salary +
                '}';
    }
}
