/**
 * This represents the date within the game.
 *
 * @author Ali.bab.la
 * @version 1.0
 */

public class Date {
    // how long player played the game
    private int date;

    /**
     * Constructor for date
     * start from day 1
     */
    public Date() {
        this.date = 1;
    }

    /**
     * Function to output date with season and year
     * @return String output in format of "Day X of Season Y, Year Z" i.e Day 21 of Fall, Year 2
     */
    public String calendarDate() {
        int date;
        if (this.getDate() > 30) {
            if (this.getDate() % 30 == 0) {
                date = 30;
            } else {
                date = this.getDate() % 30;
            }
        } else {
            date = this.getDate();
        }
        int year = this.getYear();
        int month = this.getMonth();
        return "Day " + date + " of Month " + month + ", Year " + year;
    }

    /**
     * Function to increment the date when you go to bed
     */
    public void goBed() {
        this.date += 1;
    }

    /**
     * Setter for the date
     * @param date the date you want to set to
     */
    public void setDate(int date) {
        this.date = date;
    }

    /**
     * Getter for the date
     * @return date
     */
    public int getDate() {
        return date;
    }

    /**
     * Every 30 days is a Month
     * Could be useful
     * @return int representing month
     */
    public int getMonth() {
        int month;
        if (date % 30 == 0) {
            month = date / 30;
        } else {
            month = (int) Math.floor((double) date / 30) + 1;
        }
        if (month > 12) {
            month = month - 12;
        }

        return month;
    }

    /**
     * Every 360 days is a year
     * Could be useful
     * @return int output representing year
     */
    public int getYear() {
        if (date % 360 == 0) {
            return date / 360;
        } else {
            return (int) Math.floor((double) date / 360) + 1;
        }
    }

    /**
     * 1-3 : Spring, 4-6: Summer, 7-9: Fall, 10-12: Winter
     * Could be useful
     * @return season in string output
     */
    public String getSeason() {
        String season;
        int month = this.getMonth();
        switch (month) {
        case 1:
        case 2:
        case 3:
            season = "Spring";
            break;
        case 4:
        case 5:
        case 6:
            season = "Summer";
            break;
        case 7:
        case 8:
        case 9:
            season = "Fall";
            break;
        case 10:
        case 11:
        case 12:
            season = "Winter";
            break;
        default:
            season = "";
            break;
        }
        return season;
    }
}
