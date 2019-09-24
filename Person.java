package com.example.weightloss;

import com.google.gson.Gson;

public class Person {

    private int age;
    private double current_weight;
    private double goal_weight;
    private int time_frame;			//Days

    private double height;
    private Gender gender;
    private ActivityLevel level;
    private boolean isRecommended;

    private final int CAL_PER_POUND = 3500;

    //Formulas used to extract this information
    //BMR and DNB are part of their profile, getters in Data.java let you use the info
    //Their setters are private because always done internally, only DNB has a getter because BMR
    //is only used to make DNB
    private int daily_calories;
    private double BMR;
    private double DNB;

    public Person()
    {
        this.age =0;
        this.current_weight =0;
        this.goal_weight = 0;
        this.time_frame=0;
        this.height = 0;
        this.gender = Gender.M;
        isRecommended=false;
        this.level = ActivityLevel.SEDENTARY;
    }

    public Person(int age, double current_weight, double goal_weight, int time_frame, double height, String gen, String level)
    {
        validateGender(gen);
        validateAge(age);
        validateHeight(height);
        validateWeight(current_weight);
        validateGWeight(goal_weight);
        validateActivityLevel(level);
        validateTime(time_frame);
        setBMR();
        setDNB();
        setRecommendation();
    }

    private void validateTime(int time) {
        if (time <=0)
        {
            throw new IllegalArgumentException("Enter positive numbers only");
        }
        else
        {
            this.time_frame = time;
        }
    }


    private void validateActivityLevel(String level) {
        if (level == null)
        {
            throw new IllegalArgumentException("Choose an option for activity level");
        }
        else {
            this.level = ActivityLevel.valueOf(level.toUpperCase());
        }
    }

    private void validateGender(String gen) {
        if (gender == null)
        {
            throw new IllegalArgumentException("Choose an option for gender");
        }
        else
        {
            this.gender = Gender.valueOf(gen.toUpperCase());
        }
    }

    public void setRecommendation() {
        if (daily_calories<1500)
        {
            this.isRecommended = false;
        }
        else
        {
            this.isRecommended=true;
        }
    }

    private void validateGWeight(double goal_weight) {
        if (goal_weight >0)
        {
            this.goal_weight = goal_weight;
        }
        else
        {
            throw new IllegalArgumentException("Goal weight must be greater than zero");
        }
    }

    private void validateWeight(double current_weight) {
        if (current_weight >0)
        {
            this.current_weight = current_weight;
        }
        else
        {
            throw new IllegalArgumentException("Current weight must be greater than zero");
        }
    }

    private void validateHeight(double height) {
        if (height >0)
        {
            this.height = height;
        }
        else
        {
            throw new IllegalArgumentException("Height must be greater than zero");
        }
    }

    private void validateAge(int age) {
        if (age<0 || age >120)
        {
            throw new IllegalArgumentException("Age must be greater than zero and less than 120");
        }
        else
        {
            this.age = age;
        }
    }

    public void setGender(String gender)
    {
        validateGender(gender);
    }

    public void setAge(int age)
    {
        validateAge(age);
    }

    public void setHeight(double height)
    {
        this.height = height;
    }

    public void setCurrentWeight(double weight)
    {
        this.current_weight= weight;
    }

    public void setGoalWeight(double weight)
    {
        this.goal_weight = weight;
    }

    public void setTime_frame(int time)
    {
        validateTime(time);
    }


    public void setLevel(String level)
    {
        validateActivityLevel(level);
    }

    //This method only has internal calls
    private void setBMR()
    {

        if (gender==Gender.M)
        {
            this.BMR=66.47+(6.24*current_weight)+(12.7*height)-(6.755*age);
        }
        else //need to see what to do if they x give a gender value
        {
            this.BMR=655.1 + (4.35*current_weight)+(4.7*height)-(4.7*age);
        }

    }

    //this method only has internal calls
    private void setDNB()
    {
        switch(level) {

            case SEDENTARY:
                this.DNB=this.BMR*1.2;
                break;
            case  LIGHT:
                this.DNB=this.BMR*1.375;
                break;
            case MODERATE:
                this.DNB=this.BMR*1.55;
                break;
            case VERY:
                this.DNB=this.BMR*1.725;
                break;
            case EXTRA:
                this.DNB=this.BMR*1.9;

        }
    }


    public Gender getGen()
    {
        return this.gender;
    }

    public double getAge()
    {
        return this.age;
    }

    public double getHeight()
    {
        return this.height;
    }

    public double getCurrentWeight()
    {
        return this.current_weight;
    }

    public double getGoalWeight()
    {
        return this.goal_weight;
    }


    public ActivityLevel getLevel()
    {
        return this.level;
    }

    public double getDNB()
    {
        return this.DNB;
    }

    public int getTimeFrame(){ return this.time_frame; }


    public int getDailyCalories()
    {
        setBMR();
        setDNB();
        if (current_weight>0 && goal_weight >0 && time_frame >0)

        {	double caloriesPerDay = ((current_weight - goal_weight) * CAL_PER_POUND)/time_frame;
            double caloriesEat = DNB - caloriesPerDay;
            daily_calories = (int) caloriesEat; //truncates caloriesEat into an int
            setRecommendation();
            return daily_calories;
        }
        else {
            throw new IllegalArgumentException("Cannot get Daily Calories without completing all the fields");
        }
    }

    public boolean getRecommendation()
    {
        return isRecommended;
    }

    //What do these do?
    public static Person getObjectFromJSONString (String json)
    {
        Gson gson = new Gson ();
        return gson.fromJson (json, Person.class);
    }

    public static String getJSONStringFromObject (Person object)
    {
        Gson gson = new Gson ();
        return gson.toJson (object);
    }


}
