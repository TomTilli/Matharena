# Matharena

Trailer: https://www.youtube.com/watch?v=CjPkBEI5uJc

README

    File       : README.txt
    Time-stamp : 2014-04-30T21:30 Tom Tilli
    Description: An educational mobile game, Matharena to be published in Nokia store.

GENERAL INFO

    Product name  : Matharena
    Developers    : Tom Tilli, tom.tilli@cs.tamk.fi
                  : Henna Räsänen, henna.rasanen@cs.tamk.fi
                  : Antti Terva, antti.terva@cs.tamk.fi
                  : Mikko Suojoki, mikko.suojoki@cs.tamk.fi
    Device target : Asha 503, 501 (during developement)
    Video         : https://vimeo.com/93424297 (password: java)
		              : https://vimeo.com/93424296 (password: java)

DESCRIPTION OF THE PRODUCT

    Matharena is a game where you get a mathematical calculation and you have to choose
    the correct answer from three options. The game starts really easy and gradually
    more difficult to a point where even the best will have to think for too long. The
    equation may even become long enough for a second row which will appear just for that purpose. 
    The simple mathematics are made more interesting with additional features introduced to the
    game mechanics. In story mode you will get additional features as you progress
    through the levels and in the customizable arena mode you can choose which ones you want
    to use. The graphics and features you can customize in the arena mode are unlocked by
    the story mode. Some examples of these features are Rush wich gives you only 10 seconds
    time to answers per question and Tension which makes you lose from single wrong answer
    but gives you a chance to gable all your gathered points to continue playing.
    Everything you do in this game is to gain achievements. You gain levels as you get points
    and unlock content from playing but all of these will move you closer to your next goal.

RELEASE NOTES

    Features

    Customizable equation randomizer, Saved player profile, Achievements, features introducing
    new game mechanics, profile reset, help/about, Timer.

    Known Bugs

    -Menu bug, most of the time if you try to open profile, help or options after a game or two
    the whole thing crashes. (Most likely this is caused by the next bug)
    -Thread cludder, Every new game makes additional threads for some reason. No null or
    lock could help this. interrupt() helped but made everything else go bonkers. Could
    not find a solution in time so this was not fixed.

End of file.
