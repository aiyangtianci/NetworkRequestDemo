package com.example.aiyang.allhttp_networkrequestdemo.model;

import java.util.List;

/**
 * Created by winner on 2017/5/8.
 */

public class Person {

    private List<PersonsBean> persons;

    public List<PersonsBean> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonsBean> persons) {
        this.persons = persons;
    }

    public static class personEntity{

    }

    public static class PersonsBean {
        /**
         * id : 101
         * name : 张三
         * age : 27
         */

        private String id;
        private String name;
        private int age;

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

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
