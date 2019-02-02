package com.thesoftwareguild.dvdlibraryv3.dao;


import com.swcguild.dvdlibrary.dao.DvdLibraryDao;
import com.swcguild.dvdlibrary.dto.Dvd;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Jonathan Chiou
 */
public class JonathansDao implements DvdLibraryDao {

    private String DELIMITER = "::";
    private String DVD_FILE = "dvd.txt";
    Map<Integer, Dvd> dvdMap = new HashMap<>();
     DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
   

    public Integer[] getAllDvdIds() {
        Set<Integer> keySet = dvdMap.keySet();
        Integer[] keyArray = new Integer[keySet.size()];
        keyArray = keySet.toArray(keyArray);
        return keyArray;
    }

    public void writeDvd() throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(DVD_FILE));

        Integer[] uniqueIds = this.getAllDvdIds();

        for (int i = 0; i < uniqueIds.length; i++) {

            Dvd dvd = this.getById(uniqueIds[i]);
            writer.println(dvd.getId() + DELIMITER
                    + dvd.getTitle() + DELIMITER
                    + dvd.getReleaseDate() + DELIMITER
                    + dvd.getDirector() + DELIMITER
                    + dvd.getMpaaRating() + DELIMITER
                    + dvd.getStudio() + DELIMITER
                    + dvd.getNote() + DELIMITER);
            writer.flush();
        }
        writer.close();
    }

    public void loadDvds() throws FileNotFoundException {
        Scanner sc = new Scanner(new BufferedReader(new FileReader(DVD_FILE)));
        String currentLine;
        String[] currentTokens;
        

        while (sc.hasNextLine()) {
            currentLine = sc.nextLine();
            currentTokens = currentLine.split(DELIMITER);

            Dvd dvd = new Dvd();
            dvd.setId(Integer.parseInt(currentTokens[0]));
            dvd.setTitle(currentTokens[1]);
            LocalDate releaseDate = LocalDate.parse(currentTokens[2],dtf);
            dvd.setReleaseDate(releaseDate);
            dvd.setDirector(currentTokens[3]);
            dvd.setMpaaRating(currentTokens[4]);
            dvd.setStudio(currentTokens[5]);
            dvd.setNote(currentTokens[6]);

            dvdMap.put(dvd.getId(), dvd);

        }

        sc.close();

    }

    @Override
    public void add(Dvd dvd) {
        dvdMap.put(dvd.getId(), dvd);
    }

    @Override
    public void remove(int id) {
        dvdMap.remove(id);
    }

    @Override
    public List<Dvd> listAll() {
        List<Dvd> allDvds
                = dvdMap.values()
                .stream()
                .collect(Collectors.toList());
        return allDvds;
    }

    @Override
    public Dvd getById(int id) {
        return dvdMap.get(id);
    }

    @Override
    public List<Dvd> getByTitle(String title) {
        List<Dvd> dvdsByTitle
                = dvdMap.values()
                .stream()
                .filter(d -> d.getTitle().equalsIgnoreCase(title))
                .collect(Collectors.toList());
        return dvdsByTitle;

    }

    @Override
    public List<Dvd> getByRating(String rating) {
        List<Dvd> dvdsByRating
                = dvdMap.values()
                .stream()
                .filter(m -> m.getMpaaRating().equalsIgnoreCase(rating))
                .collect(Collectors.toList());

        return dvdsByRating;
    }

    @Override
    public List<Dvd> getByStudio(String studio) {
        List<Dvd> dvdsByStudio
                = dvdMap.values()
                .stream()
                .filter(m -> m.getStudio().equalsIgnoreCase(studio))
                .collect(Collectors.toList());

        return dvdsByStudio;
    }
    
  
    public List<Dvd> getByDate(LocalDate date) {
        List<Dvd> dvdsByDate
                = dvdMap.values()
                .stream()
                .filter(m -> m.getReleaseDate().equals(date))
                .collect(Collectors.toList());

        return dvdsByDate;
    }

}