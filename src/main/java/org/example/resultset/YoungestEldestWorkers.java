package org.example.resultset;

import java.sql.Date;

public class YoungestEldestWorkers {
    private String type;
    private String name;
    private Date birthday;

    public YoungestEldestWorkers(String type, String name, Date birthday) {
        this.type = type;
        this.name = name;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return  "----> YoungestEldestWorkers ( type : '" + type + '\'' + ", name : '" + name + '\'' + ", birthday : " + birthday + " )";
    }
}
