
package com.zzolta.android.glutenfreerecipes.jsonparse;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

//@Generated("org.jsonschema2pojo")
public class Attributes {

    @Expose
    private List<String> course = new ArrayList<String>();
    @Expose
    private List<String> holiday = new ArrayList<String>();

    /**
     * @return The course
     */
    public List<String> getCourse() {
        return course;
    }

    /**
     * @param course The course
     */
    public void setCourse(List<String> course) {
        this.course = course;
    }

    public Attributes withCourse(List<String> course) {
        this.course = course;
        return this;
    }

    /**
     * @return The holiday
     */
    public List<String> getHoliday() {
        return holiday;
    }

    /**
     * @param holiday The holiday
     */
    public void setHoliday(List<String> holiday) {
        this.holiday = holiday;
    }

    public Attributes withHoliday(List<String> holiday) {
        this.holiday = holiday;
        return this;
    }
}
