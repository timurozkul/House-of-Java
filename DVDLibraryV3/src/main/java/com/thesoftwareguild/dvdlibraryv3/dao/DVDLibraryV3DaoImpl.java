package com.thesoftwareguild.dvdlibraryv3.dao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Scanner;

import java.util.stream.Collectors;
import com.swcguild.dvdlibrary.dao.DvdLibraryDao;
import com.swcguild.dvdlibrary.dto.Dvd;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 *
 * @author calarrick
 */
public class DVDLibraryV3DaoImpl implements DvdLibraryDao {

    private final String fileName = "";
    private final String DELIMITER = "::";
    
    public Map<Integer, Dvd> dvdList = new HashMap();

    
    public List<Dvd> getByDirector(String director) {

        return dvdList.values()
                .stream().filter(dvd -> dvd.getDirector().equals(director))
                .collect(Collectors.toList());

    }

    public void writeFile(String name) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(name));

        for (Map.Entry<Integer, Dvd> dvdEntry : dvdList.entrySet()) {

            Dvd dvd = dvdEntry.getValue();

            writer.print(dvd.getId() + DELIMITER
                    + dvd.getTitle() + DELIMITER
                    + dvd.getReleaseDate().toString() + DELIMITER
                    + dvd.getDirector() + DELIMITER
                    + dvd.getStudio() + DELIMITER
                    + dvd.getMpaaRating() + DELIMITER
                    + dvd.getNote() + DELIMITER);

            writer.println();

            writer.flush();
        }

        writer.close();
    }

    public void readFile(String name) throws FileNotFoundException {
        Scanner sc = new Scanner(new BufferedReader(new FileReader(name)));

        while (sc.hasNextLine()) {
            Dvd dvd = new Dvd();

            String currentLine = sc.nextLine();
            String[] currentTokens = currentLine.split(DELIMITER);

            dvd.setId(Integer.parseInt(currentTokens[0]));
            dvd.setTitle(currentTokens[1]);
            String releaseDate = (currentTokens[2]);
            dvd.setReleaseDate(LocalDate.
                    parse(releaseDate, DateTimeFormatter.ISO_DATE));
            dvd.setDirector(currentTokens[3]);
            dvd.setStudio(currentTokens[4]);
            dvd.setMpaaRating(currentTokens[5]);

            dvd.setNote(currentTokens[6]);

            dvdList.put(dvd.getId(), dvd);
        }
    }

    //--------------------------------------------------------------------------------------------
    @Override
    public void add(Dvd dvd) {
        

        dvdList.put(dvd.getId(), dvd);
    }

    @Override
    public void remove(int id) {
        dvdList.remove(id);
    }

    @Override
    public List<Dvd> listAll() {
        List<Dvd> dvds = new ArrayList();
        dvdList.values().stream().
                forEach((dvd) -> {
                    dvds.add(dvd);
                });
        return dvds;

    }

    @Override
    public Dvd getById(int id) {
        
    return dvdList.get(id);
    
    }

    @Override
    public List<Dvd> getByTitle(String title) {
        return dvdList.values().stream()
                .filter(dvd -> dvd.getTitle().equals(title))
                .collect(Collectors.toList());
    }

    @Override
    public List<Dvd> getByRating(String rating) {
        return dvdList.values()
                .stream().filter(dvd -> dvd.getMpaaRating().equals(rating))
                .collect(Collectors.toList());
    }

    @Override
    public List<Dvd> getByStudio(String studio) {
        /*return dvdList.values()
                .stream().filter(dvd -> dvd.getStudio().equals(studio))
                .collect(Collectors.toList());*/
        
        /*List<Dvd> studioList = new ArrayList();
        
        for (Map.Entry<Integer,Dvd> dvdEntry : dvdList.entrySet())
        {
            if(dvdEntry.getValue().getStudio().equals(studio)){
                studioList.add(dvdEntry.getValue());
            }
        }
        return studioList;*/
        
        return dvdList.values().stream().filter(dvd -> dvd.getStudio().equals(studio)).collect(Collectors.toList());
        
                
        
        
    }

}
