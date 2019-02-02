/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thesoftwareguild.dvdlibraryv3.app;

import com.thesoftwareguild.dvdlibraryv3.ui.ConsoleIO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.swcguild.dvdlibrary.dao.DvdLibraryDao;
import com.swcguild.dvdlibrary.dto.Dvd;
import com.thesoftwareguild.dvdlibraryv3.dao.DVDLibraryV3DaoImpl;
import com.thesoftwareguild.dvdlibraryv3.dao.JonathansDao; 
import java.time.Month;
import java.time.Period;
import java.util.Collections;

import java.util.OptionalDouble;


/**
 *
 * @author calarrick
 */
public class DVDLibraryController {

    private ConsoleIO console = new ConsoleIO();
    private DvdLibraryDao dvdLibrary;
    List<Dvd> dvdList = new ArrayList<>();
    Comparator<Dvd> titleSort = (dvd1, dvd2) -> dvd1.getTitle().compareTo(dvd2.
            getTitle());
    int nextId = 0;

    public DVDLibraryController(DvdLibraryDao dao) {
        this.dvdLibrary = dao;
    }

    public void run() {

        boolean retry = true;
        while (retry == true) {
            String select = console.
                    string("Do you want to start a new DVD library, or load one from a file? (enter \"file\" or \"new\")");
            if (!select.equals("file")) {
                retry = false;
            }

            if (select.equals("file")) {
                select = console.
                        string("Please enter the file name from which you want to load your DVD library?");
                try {
                    retry = false;
                    ((DVDLibraryV3DaoImpl) dvdLibrary).readFile(select);
                    console.printString("Loading...");
                } catch (FileNotFoundException e) {
                    select = console.
                            string("That file was not found. Would you like to try another file name? (enter \"y\" or \"n\"");
                    if (select.equals("y")) {
                        retry = true;
                    } else {
                        retry = false;
                    }
                }
            }
        }
        
        incrementId();

        printMenu();

        boolean keepAlive = true;
        String select = console.
                string("Enter the file name to save your DVD Library");
        try {
            ((DVDLibraryV3DaoImpl) dvdLibrary).writeFile(select);
            keepAlive = false;
        } catch (IOException e) {
            select = console.
                    string("There seems to be a problem saving to that file. Would you like to try again? \n Enter \"y\" or \"n\"");
            if (select.equals("n")) {
                keepAlive = false;
            }
        }

    }

    private void printMenu() {

        boolean stayAlive = true;
        while (stayAlive) {
            console.
                    printString("Please select an item from the menu. Enter the letter for your menu selection.");
            String rawResponse = console.string("L) List all DVDs \n"
                    + "T) Display full record for DVDs, found by Title \n"
                    + "D) Display records for DVDs, found by Director \n"
                    + "P) Display records for DVDs, found by MPAA Rating \n"
                    + "N) Search for recent movies \n"
                    + "A) Add a DVD to the DVD Library \n"
                    + "R) Remove a DVD from the DVD Library \n"
                    + "M) Modify an existing DVD record in the DVD Library \n"
                    + "O) Show the record for the oldest movie in the library\n"
                    + "E) Show the record for the newest movie in the library\n"
                    + "V) Show average age of movies in the library\n"
                    //                    + "S) Show average number of notes per DVD in the library \n"
                    + "U) Display movies from a particular studio\n"
                    + "X) Exit");
            String response = rawResponse.toUpperCase();

            switch (response) {
                case "L":
                    listAll();
                    break;
                case "T":
                    viewDVD();
                    break;
                case "A":
                    makeDVD();
                    break;
                case "R":
                    removeDVD();
                    break;
                case "M":
                    editDVD();
                    break;
                case "D":
                    byDirector();
                    break;
                case "P":
                    byMpaa();
                    break;
                case "N":
                    findNew();
                    break;
                case "O":
                    findOldest();
                    break;
                case "E":
                    findNewest();
                    break;
                case "V":
                    findAverage();
                    break;
//                case "S":
//                    averageNotes();
//                    break;
                case "U":
                    byStudio();
                    break;

                case "X":
                    stayAlive = false;
                    break;
            }

        }
    }

    private void editDVD() {

        Dvd selection = null;
        List<Dvd> titleMatches = new ArrayList<>();
        String select = console.
                string("What is the title of the DVD entry that you want to modify?");

        titleMatches = dvdLibrary.getByTitle(select);
        String newTitle = "";
        String newDirector = "";
        int newReleaseYear;
        String newMpaa = "";
        String newNote = "";
        LocalDate newRelease = null;
        String newStudio = "";

        if (titleMatches.size() == 1) {
            selection = titleMatches.get(0);
        }

        if (titleMatches.size() > 1) {
            console.
                    printString("There is more than one DVD in the collection by that title.\nPlease enter the number next to your selection.");
            console.printString("The following entries have the same name.");
            console.printString("Please select one of the following.");

            Integer[] selectNums = new Integer[titleMatches.size()];
            Dvd[] selectNumsPair = new Dvd[titleMatches.size()];
            for (int i = 0; i <= (titleMatches.size() - 1); i++) {

                System.out.print(i + 1 + " : " + titleMatches.get(i).
                        getTitle() + ", " + titleMatches.get(i).
                        getDirector() + ", \n" + titleMatches.get(i).
                        getReleaseDate()
                        + titleMatches.get(i).getNote() + "\n\n");
//                List<String> notes = titleMatches.get(i).getNote();
//                for (String note: notes)
//                {
//                System.out.print(note);
//                }
                selectNumsPair[i] = dvdLibrary.getById(titleMatches.get(i).
                        getId());
                selectNums[i] = i;//to correct for hash ordering of a map I'm using parallel arrays instead
            }
            int choice = console.
                    getInt("\nPlease select the number next to the correct entry.");
            if (choice > selectNumsPair.length || choice - 1 < 0) {
                console.printString("Please make a valid selection.");
            } else {
                selection = selectNumsPair[choice - 1];
            }
        }
        if (selection != null) {
            console.printString("The title is now: " + selection.getTitle());
            String titleChoice = console.
                    string("Do you want to edit the title? (\"y\" or \"n\")");
            if (titleChoice.equals("y")) {
                newTitle = console.string("What is the new title? ");
                newTitle = newTitle.concat(" ");

            } else {
                newTitle = selection.getTitle();
            }

            console.printString("The director is now: " + selection.
                    getDirector());
            String directorChoice = console.
                    string("Do you want to edit the director? (\"y\" or \"n\")");
            if (directorChoice.equals("y")) {
                newDirector = console.string("What is the new director's name?");
                newDirector = newDirector.concat(" ");
            } else {
                newDirector = selection.getDirector();

            }

            console.printString("The year of release is now: " + selection.
                    getReleaseDate());
            String releaseChoice = console.
                    string("Do you want to edit the year of release? (\"y\" or \"n\")");
            if (releaseChoice.equals("y")) {
                newReleaseYear = console.
                        getInt("What is the correct release year?");
                newRelease = LocalDate.of(newReleaseYear, Month.DECEMBER, 30);
            } else {
                newRelease = selection.getReleaseDate();

            }

            console.printString("The MPAA rating is now: " + selection.
                    getMpaaRating());
            String mpaaChoice = console.
                    string("Do you want to edit the movie's rating? (\"y\" or \"n\")");
            if (mpaaChoice.equals("y")) {
                newMpaa = console.string("What is the new MPAA rating?");

            } else {
                newMpaa = selection.getMpaaRating();
            }

            console.printString("The studio is now: " + selection.getStudio());
            String studioChoice = console.
                    string("Do you want to change the movie's studio? (\"y\" or \"n\")");
            if (studioChoice.equals("y")) {
                newStudio = console.string("What is the new Studio?");
            } else {
                newStudio = selection.getStudio();
            }

            console.printString("Your notes are now: " + selection.getNote());
            String noteChoice = console.
                    string("Do you want to change the note? (\"y\" or \"n\")");
            if (noteChoice.equals("y")) {

                newNote = console.string("What is the new note? ");
            } else {
                newNote = selection.getNote();
            }

//            List<String> modNotes = selection.getNote();
//            for (String note : modNotes) {
//                console.printString(note);
//            }
//
//            String noteChoice = console.
//                    string("Do you want to add a note about the DVD? (\"y\" or \"n\")");
//            if (noteChoice.toLowerCase().equals("y")) {
//                newNote = console.string("What is your new note?");
//                modNotes.add(newNote);
//
//            } else {
//                // newNote = selection.getNote();
//            }
//            noteChoice = console.
//                    string("Do you want to remove a note about the DVD? (\"y\" or \"n\")");
//            if (noteChoice.toLowerCase().equals("y")) {
//                console.printString("Select the note you want to remove: \n");
//                for (String note : modNotes) {
//
//                    console.printString((modNotes.indexOf(note) + 1) + ": ");
//                    console.printString(note + "\n");
//                }
//                int max = modNotes.size();
//
//                int choice = console.
//                        getInt("\nEnter the number next to the note you want to remove: ");
//                while (choice < 1 || choice > modNotes.size()) {
//                    choice = console.
//                            getInt("I need a number matching the above menu of options. Thank you.");
//                }
//
//                select = console.
//                        string("Remove this comment (\"y\" or \"n\"): " + "\n" + modNotes.
//                                get(choice - 1));
//
//                if (select.toLowerCase().equals("y")) {
//
//                    modNotes.remove(choice - 1);
//                }
//
        }

        console.printString("Here is the edited information: ");
        console.
                printString(newTitle + "\n" + newDirector + "\n" + newRelease + "\n" + newMpaa + "\n" + newNote);
        String replace = console.
                string("Do you want to replace the old information with the above? (\"y\" or \"n\")");
        if (replace.equals("y")) {

            selection.setDirector(newDirector);
            selection.setMpaaRating(newMpaa);
            selection.setTitle(newTitle);
            selection.setStudio(newStudio);
            selection.setNote(newNote);

        }
    }

    private void removeDVD() {

        Dvd selection = null;
        List<Dvd> titleMatches = new ArrayList<>();
        String select = console.
                string("What is the title of the DVD entry that you want to remove?");

        titleMatches = dvdLibrary.getByTitle(select);

        if (titleMatches.size() < 1) {
            System.out.
                    println("There is no movie with that title in your library.\n\n");
            return;
        }

        if (titleMatches.size() == 1) {
            selection = titleMatches.get(0);
        } else {
            console.
                    printString("There are more than one DVD in the collection by that title.\nPlease enter the number next to your selection.");
            console.printString("The following entries have the same name.");
            console.printString("Please select one of the following.");

            Integer[] selectNums = new Integer[titleMatches.size()];
            Dvd[] selectNumsPair = new Dvd[titleMatches.size()];
            for (int i = 0; i <= (titleMatches.size() - 1); i++) {

                System.out.print(i + 1 + " : " + titleMatches.get(i).
                        getTitle() + ", " + titleMatches.get(i).
                        getDirector() + ", \n" + titleMatches.get(i).
                        getReleaseDate()
                        + titleMatches.get(i).getNote());
//                List<String> notes = titleMatches.get(i).getNote();
//                        notes.stream().forEach(note -> System.out.
//                                print(", " + note + ", "));
//                        System.out.println("\n\n");

                selectNumsPair[i] = dvdLibrary.getById(titleMatches.get(i).
                        getId());
                selectNums[i] = i;//to correct for hash ordering of a map I'm using parallel arrays instead
            }

            int choice = 0;
            while (choice > selectNumsPair.length || choice - 1 < 0) {
                choice = console.
                        getInt("\nPlease select the number next to the correct entry.");
            }

            selection = selectNumsPair[choice - 1];

        }

        System.out.print(selection.getTitle() + "\n"
                + "Director: " + selection.getDirector() + "\n"
                + "Released: " + selection.getReleaseDate() + "\n"
                + "Rated (MPAA): " + selection.getMpaaRating() + "\n"
                + "My Note: " + selection.getNote());
//        List<String> notes = selection.getNote();
//        notes.stream().forEach(note -> System.out.
//                print(note + "\n"));
//        System.out.println("\n\n");

        String confirm = console.
                string("Do you want to delete this DVD record from the library? (\"y\" or \"n\")");
        if (confirm.equals("y")) {
            dvdLibrary.remove(selection.getId());
        }

    }

    private void viewDVD() {

        List<Dvd> titleMatches = new ArrayList<>();
        String select = console.
                string("What is the title of the DVD you are looking for?");

        titleMatches = dvdLibrary.getByTitle(select);
        if (titleMatches.size() > 1) {
            console.
                    printString("There are " + titleMatches.size() + " DVDs by that name in your collection.");
        }
        for (Dvd dvd : titleMatches) {
            System.out.print(dvd.getTitle() + "\n"
                    + "Director: " + dvd.getDirector() + "\n"
                    + "Released: " + dvd.getReleaseDate() + "\n"
                    + "Rated (MPAA): " + dvd.getMpaaRating() + "\n"
                    + "My Note: " + dvd.getNote());

            System.out.println("\n\n");

        }

    }

    private void makeDVD() {

        String setTitle = console.string("What is the title of the movie? \n");
        //setTitle = setTitle.concat(" ");
        setTitle = setTitle.trim();
        String setDirector = console.
                string("What is the name of the director of the movie? \n");
        //setDirector = setDirector.concat(" ");
        setDirector = setDirector.trim();
        int setReleaseYear = console.
                getInt("Enter the year in which the movie was released: \n");

        String newStudio = console.string("What studio released the movie: \n");
        newStudio = newStudio.trim();

        String setMpaaRating = console.
                string("What rating did the movie get from the MPAA? \n");
        //setMpaaRating = setMpaaRating.concat(" ");
        setMpaaRating = setMpaaRating.trim();

        String setNote = console.
                string("Enter your own note about the movie or this DVD: \n");
        //setNote = setNote.concat(" ");
        setNote = setNote.trim();
//        List<String> setNotes = new ArrayList<>();
//        setNotes.add(setNote);

//        String addNote = console.string("Enter another note (\"y\" or \"n\")?");
//        while (addNote.toLowerCase().equals("y")) {
//
//            setNote = console.
//                    string("Enter your additional note about this movie or DVD: \n");
//            setNotes.add(setNote);
//            addNote = console.string("\nEnter another note (\"y\" or \"n\")?");
//        }
        Dvd newDvd = new Dvd();
        newDvd.setTitle(setTitle);
        newDvd.setDirector(setDirector);
        newDvd.setStudio(newStudio);
        newDvd.setReleaseDate(LocalDate.of(setReleaseYear, Month.DECEMBER, 30));
        newDvd.setMpaaRating(setMpaaRating);
        newDvd.setNote(setNote);
        newDvd.setId(nextId);
        
        dvdLibrary.add(newDvd);
           

    }

    private void listAll() {

        List<Dvd> dvdsList = dvdLibrary.listAll();
        dvdsList.stream().sorted(titleSort).forEach(dvd
                -> System.out.print(dvd.getTitle() + "\n"
                        + "Director: " + dvd.getDirector() + "\n"
                        + "Released: " + dvd.getReleaseDate() + "\n"
                        + "Rated (MPAA): " + dvd.getMpaaRating() + "\n\n"));

        /*List<String> titles = new ArrayList<>();
         Map<String, DVD> titleMatch = new HashMap<>();
         //generating alphabetized list, and rematching to sorted titles
         for (int i = 1; i <= dvdsList.size(); i++) {
         //going to de-dupe with title + id, get title back from list, then use ID to match on main id-keyed map
         //avoid dupe key weirdness with dupe titles that way
         titles.add(dvdsList.get(i - 1).getTitle() + " " + dvdsList.
         get(i - 1).getDvdId());
         titleMatch.put(dvdsList.get(i - 1).getTitle() + " " + dvdsList.
         get(i - 1).getDvdId(), dvdsList.get(i - 1));
         }
         titles.sort(null);
        
        
         for (String title : titles) {

         System.out.print(titleMatch.get(title).getTitle() + "\n"
         + "Director: " + titleMatch.get(title).getDirector() + "\n"
         + "Released: " + titleMatch.get(title).getReleaseYear() + "\n"
         + "Rated (MPAA): " + titleMatch.get(title).getMpaaRating() + "\n\n");
         }*/
    }

    private void byDirector() {

        String director = console.string("What is the name of the director? ");

        List<Dvd> directorList = ((DVDLibraryV3DaoImpl) dvdLibrary).
                getByDirector(director);

        System.out.println("Movies by: " + director);

        Map<String, List<Dvd>> directorByMpaa
                = directorList.stream()
                .collect(Collectors.groupingBy(Dvd::getMpaaRating));

        directorByMpaa.keySet().stream().forEach(mpaa -> {
            System.out.print("\nRated: " + mpaa + "\n");

            directorByMpaa.get(mpaa).stream().sorted(titleSort).forEach(dvd
                    -> {
                        System.out.print(dvd.getTitle() + "\n"
                                + "Released: " + dvd.getReleaseDate() + "\n"
                                + "Note: " + dvd.getNote() + "\n\n");
                    });
        });
    }

    private void findNew() {

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        int select = console.
                getInt("You want to see movies within the last how many years? (enter a number)");
        List<Dvd> eligible = dvdLibrary.listAll().stream().filter(movie -> movie.getReleaseDate().until(LocalDate.now()).
                    getYears() < select).collect(Collectors.toList());
        
//        List<Dvd> withinYears = allYears.stream().filter(dvd -> today.
//                minusYears(select).isBefore(LocalDate.
//                        parse(dvd.getReleaseDate().toString() + "1231", formatter))).
//                collect(Collectors.toList());

        console.
                printString("Movies released within the past " + select + " years: \n\n");

        eligible.stream().sorted(titleSort).forEach(dvd -> {
            System.out.print(dvd.getTitle() + "\n"
                    + "Director: " + dvd.getDirector() + "\n"
                    + "Released: " + dvd.getReleaseDate() + "\n"
                    + "Rated (MPAA): " + dvd.getMpaaRating() + "\n"
                    + "Notes: " + dvd.getNote() + "\n\n");
        });

    }

    private void findNewest() {

        List<Dvd> allMovies = dvdLibrary.listAll();
        Period shortestP = null;
        List<Dvd> newestMovies = new ArrayList<>();

        List<Integer> years = new ArrayList<>();
        allMovies.stream().forEach(dvd -> {
            int yearsOld = dvd.getReleaseDate().until(LocalDate.now()).
                    getYears();
            years.add(yearsOld);
        });

        int leastOld = Collections.min(years);

        for (Dvd movie : allMovies) {

            int yearsOld = movie.getReleaseDate().until(LocalDate.now()).
                    getYears();
            if (yearsOld == leastOld) {

                newestMovies.add(movie);
            }

        }

        if (newestMovies.size() == 1) {
            System.out.println("The newest movie is: \n\n");
        } else {

            System.out.println("The newest movies are: \n\n");
        }

        newestMovies.stream().sorted(titleSort).forEach(dvd -> {
            System.out.print(dvd.getTitle() + "\n"
                    + "Director: " + dvd.getDirector() + "\n"
                    + "Released: " + dvd.getReleaseDate() + "\n"
                    + "Rated (MPAA): " + dvd.getMpaaRating() + "\n"
                    + "Note: " + dvd.getNote() + "\n\n");
        });

    }

    private void findOldest() {

        List<Dvd> allMovies = dvdLibrary.listAll();
        Period longestP = null;
        List<Dvd> oldestMovies = new ArrayList<>();

        List<Integer> years = new ArrayList<>();
        allMovies.stream().forEach(dvd -> {
            int yearsOld = dvd.getReleaseDate().until(LocalDate.now()).
                    getYears();
            years.add(yearsOld);
        });

        int leastOld = Collections.max(years);

        for (Dvd movie : allMovies) {

            int yearsOld = movie.getReleaseDate().until(LocalDate.now()).
                    getYears();
            if (yearsOld == leastOld) {

                oldestMovies.add(movie);
            }

        }

        if (oldestMovies.size() == 1) {
            System.out.println("The oldest movie is: \n\n");
        } else {

            System.out.println("The oldest movies are: \n\n");
        }

        oldestMovies.stream().sorted(titleSort).forEach(dvd -> {
            System.out.print(dvd.getTitle() + "\n"
                    + "Director: " + dvd.getDirector() + "\n"
                    + "Released: " + dvd.getReleaseDate() + "\n"
                    + "Rated (MPAA): " + dvd.getMpaaRating() + "\n"
                    + "Note: " + dvd.getNote() + "\n\n");
        });

    }

    private void findAverage() {

        List<Dvd> allMovies = dvdLibrary.listAll();
        List<Integer> years = new ArrayList<>();
        allMovies.stream().forEach(dvd -> {
            int yearsOld = dvd.getReleaseDate().until(LocalDate.now()).
                    getYears();
            years.add(yearsOld);
        });

        System.out.print("The average age of your movies is: ");
        OptionalDouble average = years
                .stream()
                .mapToDouble(a -> a)
                .average();
        System.out.print(average.getAsDouble() + " years old.");

    }
    /*
     List<Dvd> allMovies = dvdLibrary.listAll();

     double averageRelYear;

     averageRelYear = allMovies.stream().mapToInt(Dvd::getReleaseYear).
     average().getAsDouble();

     int averageAge = (int) (2015 - averageRelYear);
     //deliberate "lossy" cast... discarding the remaining decimal points after a round year is what I want here

     System.out.
     println("The average age of movies in this DVD collection is about " + averageAge + " years.");

     }*/

//    private void averageNotes() {
//
//        List<Dvd> allMovies = dvdLibrary.listAll();
//        double avNotes = 0;
//
//        /*return allMovies.stream().forEach(dvd -> {
//         List<String> notes = dvd.getNote();
//         int count = notes.size();
//         }
//         );
//         */
//        OptionalDouble average = allMovies.stream().mapToInt(dvd -> dvd.
//                getNote().size()).average();
//        if (average.isPresent()) {
//            avNotes = average.getAsDouble();
//        }
//
//        console.
//                printString("The average number of notes you have added to your DVDs is " + avNotes);
//
//    }
    private void byMpaa() {

        String rating = console.string("What MPAA Rating are you looking for? ");

        List<Dvd> ratedList = dvdLibrary.getByRating(rating);

        System.out.println("Movies rated: " + rating + "\n");
        ratedList.stream().sorted(titleSort).forEach(dvd
                -> {
                    System.out.print(dvd.getTitle() + "\n"
                            + "Director: " + dvd.getDirector() + "\n"
                            + "Released: " + dvd.getReleaseDate() + "\n"
                            + "Rated (MPAA): " + dvd.getMpaaRating() + "\n"
                            + "Notes: " + dvd.getNote() + "\n\n");
                });
    }

    private void byStudio() {

        String studio = console.string("What studio are you looking for? ");

        List<Dvd> ratedList = dvdLibrary.getByStudio(studio);

        System.out.println("Movies released by: " + studio + "\n");
        ratedList.stream().sorted(titleSort).forEach(dvd
                -> {
                    System.out.print(dvd.getTitle() + "\n"
                            + "Director: " + dvd.getDirector() + "\n"
                            + "Released: " + dvd.getReleaseDate() + "\n"
                            + "Rated (MPAA): " + dvd.getMpaaRating() + "\n"
                            + "Notes: " + dvd.getNote() + "\n\n");
                });
    }

    private void incrementId() {
       
        nextId = 1 + (dvdLibrary.listAll().stream().mapToInt((dvd) -> dvd.getId()).max().getAsInt());
        
    
    }

}
