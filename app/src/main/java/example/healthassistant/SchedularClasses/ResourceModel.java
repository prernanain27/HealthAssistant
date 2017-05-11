package example.healthassistant.SchedularClasses;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Neha on 5/7/2017.
 */

public class ResourceModel {

    private Boolean feasible;

    private String duration;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    private ArrayList<JobModel> processor;
    private ArrayList<JobModel> resource_interferer;
    private ArrayList<MedScheduleItem> schedule;

    public Boolean getFeasible() {
        return feasible;
    }

    public void setFeasible(Boolean feasible) {
        this.feasible = feasible;
    }



    public ArrayList<JobModel> getProcessor() {
        return processor;
    }

    public void setProcessor(ArrayList<JobModel> processor) {
        this.processor = processor;
    }

    public ArrayList<JobModel> getResource_interferer() {
        return resource_interferer;
    }

    public void setResource_interferer(ArrayList<JobModel> resource_interferer) {
        this.resource_interferer = resource_interferer;
    }

    public ArrayList<MedScheduleItem> getSchedule() {
        return schedule;
    }

    public void setSchedule(ArrayList<MedScheduleItem> schedule) {
        this.schedule = schedule;
    }

    public ResourceModel()
    {

    }



    public  void createResourceModel()
    {

    }
}
