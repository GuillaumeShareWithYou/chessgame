/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Marie_Estelle
 */
public class Time {
    private int minute;
    private int sec;
    
    public Time(Time _t)
    {
        this.minute = _t.minute;
        this.sec = _t.sec;
    }
    public Time()
    {
        this.minute = 0;
        this.sec = 0;
    }
    public Time(int _m, int _s)
    {
        this.minute = _m;
        this.sec = _s;
    }
    public void incrementerMinute()
    {
        this.minute ++;
    }
    public void incrementerSec()
    {
        this.sec ++;
        if(sec >= 60)
        {
            this.minute += this.sec / 60;
            this.sec = sec % 60;
        }
    }
    public void decrementerMinute()
    {
        this.minute --;
        if(this.minute < 0)
        {
            this.minute = 0;
        }
    }
    
    public void decrementerSec()
    {
        this.sec --;
        if(this.sec < 0)
        {
            if(this.minute > 0)
            {
                this.decrementerMinute();
                this.sec = 59;
            }else{
                this.sec = 0;
            }
        }
    }
    public boolean isZero()
    {
        return (this.minute == 0 && this.sec == 0);
    }
    
    @Override
    public String toString()
    {
        if(this.sec < 10)
        {
            return (this.minute+" : 0"+this.sec);
        }
        return (this.minute+" : "+this.sec);
    }
    public boolean lessThan(int _min, int _sec)
    {
        if(this.minute <= _min)
        {
            if(this.sec < _sec)
            {
                return true;
            }
        }
        return false;
    }
}
