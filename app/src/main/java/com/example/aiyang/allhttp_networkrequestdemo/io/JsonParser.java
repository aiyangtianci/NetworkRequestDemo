package com.example.aiyang.allhttp_networkrequestdemo.io;

import com.example.aiyang.allhttp_networkrequestdemo.model.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by winner on 2017/5/8.
 */

public class JsonParser {
    public static List<Person.PersonsBean> parse(String jsonString){
        List<Person.PersonsBean> persons = new ArrayList<>();

        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("persons");
            int length = jsonArray.length();
            for(int i = 0; i < length; i++){
                JSONObject personObject = jsonArray.getJSONObject(i);
                String id = personObject.getString("id");
                String name = personObject.getString("name");
                int age = personObject.getInt("age");
                Person.PersonsBean person = new Person.PersonsBean();
                person.setId(id);
                person.setName(name);
                person.setAge(age);
                persons.add(person);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return persons;
    }
}
