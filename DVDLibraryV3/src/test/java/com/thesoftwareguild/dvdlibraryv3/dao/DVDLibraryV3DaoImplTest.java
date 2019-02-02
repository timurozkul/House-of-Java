/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thesoftwareguild.dvdlibraryv3.dao;

import com.swcguild.dvdlibrary.dao.DvdLibraryDao;
import com.swcguild.dvdlibrary.dto.Dvd;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author apprentice
 */
public class DVDLibraryV3DaoImplTest {

    DvdLibraryDao dvdLibrary;

    public DVDLibraryV3DaoImplTest() {
    }

    @Before
    public void setUp() {

        dvdLibrary = new DVDLibraryV3DaoImpl();
    }

    @After
    public void tearDown() {
        dvdLibrary = null;
    }

    @Test
    public void DVDLibraryReadWriteTest() {
        DvdLibraryDao dvdLibrary2 = new DVDLibraryV3DaoImpl();

        Dvd dvd1 = new Dvd();
        dvd1.setId(1);
        dvd1.setTitle("Star Wars");
        dvd1.setReleaseDate(LocalDate.of(1977, Month.MAY, 25));
        dvd1.setDirector("George Lucas");
        dvd1.setStudio("Twentieth Century Fox");
        dvd1.setMpaaRating("PG");
        String note = "It was okay";
        dvd1.setNote(note);

        Dvd dvd2 = new Dvd();
        dvd2.setId(2);
        dvd2.setTitle("Jurassic Park");
        dvd2.setReleaseDate(LocalDate.of(1994, Month.JUNE, 11));
        dvd2.setDirector("Some Dude");
        dvd2.setStudio("Universal Pictures");
        dvd2.setMpaaRating("PG-13");
        note = "It was okay";
        dvd2.setNote(note);

        Dvd dvd3 = new Dvd();
        dvd3.setId(3);
        dvd3.setTitle("Some Movie");
        dvd3.setReleaseDate(LocalDate.of(1987, Month.MARCH, 15));
        dvd3.setDirector("Some Other Guy");
        dvd3.setStudio("Backlot Tedium");
        dvd3.setMpaaRating("R");
        dvd3.setNote("blah blah blah");
        //testing read/write requires the down cast
        //b/c not specified in interface
        ((DVDLibraryV3DaoImpl) dvdLibrary).dvdList.put(dvd1.getId(), dvd1);
        ((DVDLibraryV3DaoImpl) dvdLibrary).dvdList.put(dvd2.getId(), dvd2);
        ((DVDLibraryV3DaoImpl) dvdLibrary).dvdList.put(dvd3.getId(), dvd3);

        try {
            ((DVDLibraryV3DaoImpl) dvdLibrary).writeFile("test.txt");
            ((DVDLibraryV3DaoImpl) dvdLibrary).readFile("test.txt");
        } catch (Exception e) {

        }

        List<String> titleList = new ArrayList<>();

        ((DVDLibraryV3DaoImpl) dvdLibrary).dvdList.values().stream()
                .forEach(dvd -> titleList.add(dvd.getTitle()));

        List<String> titleList2 = new ArrayList<>();

        ((DVDLibraryV3DaoImpl) dvdLibrary).dvdList.values().stream()
                .forEach(dvd -> titleList2.add(dvd.getTitle()));

        assertEquals(titleList, titleList2);
    }

    @Test
    public void DVDLibraryListAllTest() {
        Dvd dvd1 = new Dvd();
        dvd1.setId(1);
        dvd1.setTitle("Star Wars");
        dvd1.setReleaseDate(LocalDate.of(1977, Month.MAY, 25));
        dvd1.setDirector("George Lucas");
        dvd1.setStudio("Twentieth Century Fox");
        dvd1.setMpaaRating("PG");
        String note = "It was okay";
        dvd1.setNote(note);

        Dvd dvd2 = new Dvd();
        dvd2.setId(2);
        dvd2.setTitle("Jurassic Park");
        dvd2.setReleaseDate(LocalDate.of(1994, Month.JUNE, 11));
        dvd2.setDirector("Some Dude");
        dvd2.setStudio("Universal Pictures");
        dvd2.setMpaaRating("PG-13");
        note = "It was okay";
        dvd2.setNote(note);

        Dvd dvd3 = new Dvd();
        dvd3.setId(3);
        dvd3.setTitle("Some Movie");
        dvd3.setReleaseDate(LocalDate.of(1987, Month.MARCH, 15));
        dvd3.setDirector("Some Other Guy");
        dvd3.setStudio("Backlot Tedium");
        dvd3.setMpaaRating("R");
        dvd3.setNote("blah blah blah");

        dvdLibrary.add(dvd3);
        dvdLibrary.add(dvd2);
        dvdLibrary.add(dvd1);

        List<Dvd> expected = new ArrayList();
        expected.add(dvd1);
        expected.add(dvd2);
        expected.add(dvd3);
        List<Dvd> result = dvdLibrary.listAll();
        assertEquals(expected.size(), result.size());
    }

    @Test
    public void DVDLibraryGetByTitleTest() {
        Dvd dvd1 = new Dvd();
        dvd1.setId(1);
        dvd1.setTitle("Star Wars");
        dvd1.setReleaseDate(LocalDate.of(1977, Month.MAY, 25));
        dvd1.setDirector("George Lucas");
        dvd1.setStudio("Twentieth Century Fox");
        dvd1.setMpaaRating("PG");
        String note = "It was okay";
        dvd1.setNote(note);

        Dvd dvd2 = new Dvd();
        dvd2.setId(2);
        dvd2.setTitle("Jurassic Park");
        dvd2.setReleaseDate(LocalDate.of(1994, Month.JUNE, 11));
        dvd2.setDirector("Some Dude");
        dvd2.setStudio("Universal Pictures");
        dvd2.setMpaaRating("PG-13");
        note = "It was okay";
        dvd2.setNote(note);

        Dvd dvd3 = new Dvd();
        dvd3.setId(3);
        dvd3.setTitle("Some Movie");
        dvd3.setReleaseDate(LocalDate.of(1987, Month.MARCH, 15));
        dvd3.setDirector("Some Other Guy");
        dvd3.setStudio("Buy and Large Studios");
        dvd3.setMpaaRating("R");
        dvd3.setNote("blah blah blah");
        Dvd dvd4 = new Dvd();
        dvd4.setId(4);
        dvd4.setTitle("Some Movie");
        dvd3.setReleaseDate(LocalDate.of(1923, Month.MAY, 15));
        dvd3.setStudio("Smallco");
        dvd4.setDirector("Some Other Guy");
        dvd4.setMpaaRating("R");
        dvd4.setNote("Good");

        dvdLibrary.add(dvd1);
        dvdLibrary.add(dvd2);
        dvdLibrary.add(dvd3);
        dvdLibrary.add(dvd4);

        List<Dvd> expected = new ArrayList();
        expected.add(dvd3);
        expected.add(dvd4);
        List<Dvd> result = dvdLibrary.getByTitle("Some Movie");
        assertEquals(expected, result);
    }

    @Test
    public void DVDLibraryRemoveDVDTest() {
        Dvd dvd1 = new Dvd();
        dvd1.setId(1);
        dvd1.setTitle("Star Wars");
        dvd1.setReleaseDate(LocalDate.of(1977, Month.MAY, 25));
        dvd1.setDirector("George Lucas");
        dvd1.setStudio("Twentieth Century Fox");
        dvd1.setMpaaRating("PG");
        String note = "It was okay";
        dvd1.setNote(note);

        
        dvdLibrary.add(dvd1);

        dvdLibrary.remove(dvd1.getId());

        Dvd expected = null;
        Dvd result = dvdLibrary.getById(1);

        assertEquals(expected, result);

    }

  @Test
    public void testGetByStudio() {

        Dvd dvd1 = new Dvd();
        dvd1.setId(1);
        dvd1.setTitle("Star Wars");
        dvd1.setReleaseDate(LocalDate.of(1977, Month.MAY, 25));
        dvd1.setDirector("George Lucas");
        dvd1.setStudio("Twentieth Century Fox");
        dvd1.setMpaaRating("PG");
        String note = "It was okay";
        dvd1.setNote(note);

        Dvd dvd2 = new Dvd();
        dvd2.setId(2);
        dvd2.setTitle("Jurassic Park");
        dvd2.setReleaseDate(LocalDate.of(1994, Month.JUNE, 11));
        dvd2.setDirector("Some Dude");
        dvd2.setStudio("Universal Pictures");
        dvd2.setMpaaRating("PG-13");
        note = "It was okay";
        dvd2.setNote(note);

        Dvd dvd3 = new Dvd();
        dvd3.setId(3);
        dvd3.setTitle("Some Movie");
        dvd3.setReleaseDate(LocalDate.of(1987, Month.MARCH, 15));
        dvd3.setDirector("Some Other Guy");
        dvd3.setStudio("Buy and Large Studios");
        dvd3.setMpaaRating("R");
        dvd3.setNote("blah blah blah");
        Dvd dvd4 = new Dvd();
        dvd4.setId(4);
        dvd4.setTitle("Some Movie");
        dvd4.setReleaseDate(LocalDate.of(1923, Month.MAY, 15));
        dvd4.setStudio("Smallco");
        dvd4.setDirector("Some Other Guy");
        dvd4.setMpaaRating("R");
        dvd4.setNote("Good");

        dvdLibrary.add(dvd1);
        dvdLibrary.add(dvd2);
        dvdLibrary.add(dvd3);
        dvdLibrary.add(dvd4);

        List<Dvd> expResultList = new ArrayList();
        expResultList.add(dvd3);

        List<Dvd> result = dvdLibrary.getByStudio("Smallco");

        

        assertEquals(expResultList.get(0).getDirector(), result.get(0).getDirector());

    }

}
