package com.abumanga.countdowncalender;

import android.widget.TextView;

public class Quotes
{
    String[] otherDays = {
            "The trouble is, you think you think you have time.\n~ J. Kornfield",
            "Time is extremely valuable.\n~ Kanye West",
            "There's never enough time to do all the nothing you want.\n~ B. Watterson",
            "Oh yes, the past can hurt. But you can either run from it or learn from it.\n~ Rafiki (The Lion King)",
            "Today is a good day to try.\n~ Quasimodo (The Hunchback of Notre Dame)",
            "You may not own this day, but you can use it.\n~ H. Mackey",
            "Time is money.\n~ B. Franklin",
            "Time is a storm in which we are all lost.\n~ W. C. Williams",
            "Suspect each moment, for it is a thief, tiptoeing away with more than it brings.\n~ J. Updike",
            "Men talk of killing time, while time quietly kills them.\n~ D. Boucicault",
            "Regret for wasted time is more wasted time.\n~ M. Cooley",
            "Time spent with cats is never wasted.\n~ Colette",
            "The way we spend our time defines who we are.\n~ J. Estrin",
            "It's not that we have little time, but more that we waste a good deal of it.\n~ Seneca",
            "Either you run the day, or the day runs you.\n~ J. Rohn",
            "If we take care of the moments, the years will take care of themselves.\n~ M. Edgeworth"
    };
    public Quotes(TextView eventLabel) {
        int rand = (int) (Math.random() * otherDays.length);
        eventLabel.setText(otherDays[rand]);
    }
}
